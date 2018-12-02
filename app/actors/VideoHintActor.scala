package actors

import java.time.Instant

import actors.messages.ActorFailed
import actors.messages.level.Stats
import akka.actor.{Actor, ActorRef, Props}
import akka.http.scaladsl.util
import akka.http.scaladsl.util.FastFuture
import com.google.inject.Inject
import daos.{StatsDAO, VideoHintDAO}
import play.api.Environment
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global

object VideoHintActor {
  final case class GetVideo()
  final case class GetHintsTaken()
  final case class HintPrepared(videoUrl: String, remainingTime: RemainingTime)
  final case class RemainingTime(continentId: String, videoCount: Int, stars: Int, remainingTime: Long)
  final case class RemainingTimeList(tokenId: String, list: List[RemainingTime])
  final case class GetHintData(path: String)
  final case class NoHints()

  def embedURL(videoId: String): String = s"https://www.youtube.com/embed/$videoId?rel=0"

  def generateTimestamp: Long = Instant.now.getEpochSecond

  def calculateStars(stars: Int, videoCount: Int): Int = {
    val starRatio = Math.ceil(3.toDouble / videoCount).toInt
    Math.max(0, stars - starRatio)
  }

  private val starExpiration = 3600 // bump up to 3600

  def calculateRemainingTime(timeStamp: Long, expLength: Int): Long = {
    val expiredTime = timeStamp + expLength
    val timeNow = generateTimestamp
    val remainingTime = expiredTime - timeNow

    if (remainingTime <= 0) {
      0
    } else {
      remainingTime / 60
    }
  }

  def props(out: ActorRef,
            tokenId: String,
            statsDAO: StatsDAO,
            levelControl: LevelControl,
            videoHintDAO: VideoHintDAO,
            ws: WSClient,
            environment: Environment) =
    Props(new VideoHintActor(out, tokenId, statsDAO, levelControl, videoHintDAO, ws, environment))
}

class VideoHintActor @Inject()(out: ActorRef,
                               tokenId: String,
                               statsDAO: StatsDAO,
                               levelControl: LevelControl,
                               videoHintDAO: VideoHintDAO,
                               ws: WSClient,
                               environment: Environment)
    extends Actor {
  import VideoHintActor._

  override def receive: Receive = {
    /*
     * GetVideo - entry point for getting a video
     * */
    case GetVideo() =>
      for {
        path <- levelControl.getPath(tokenId)
      } yield {
        val videoIds = levelControl.getVideoIds(path)
        if (videoIds.nonEmpty) {
          for {
            hintsTaken <- videoHintDAO.updateOrAdd(tokenId, path, videoIds.length)
          } yield {
            val hint = hintsTaken.list(path)
            val count = hint.count
            val videoId = if (count > videoIds.length - 1) videoIds.last else videoIds(count - 1)
            val videoUrl = embedURL(videoId)
            val timeStamp = hint.timeStamp
            out ! HintPrepared(
              videoUrl,
              RemainingTime(path, count, hint.stars, calculateRemainingTime(timeStamp, starExpiration))
            )
          }
        } else {
          out ! NoHints()
        }
      }
    /*
     * GetHintsTaken - returns a list of hints taken with a remaining time in minutes
     * also filters out expired videos.
     * */
    case GetHintsTaken() =>
      for {
        hintsTakenOpt <- videoHintDAO.getHints(tokenId)
      } yield
        hintsTakenOpt match {
          case Some(hintsTaken) =>
            val resetList = hintsTaken.list.map { ht =>
              if (calculateRemainingTime(ht._2.timeStamp, starExpiration) == 0) {
                ht._1 -> ht._2.copy(count = 0)
              } else ht
            }
            for {
              _ <- videoHintDAO.replaceList(tokenId, resetList)
            } yield
              out ! RemainingTimeList(
                tokenId,
                resetList.values
                  .filter(_.count > 0)
                  .map { ht =>
                    val videoIds = levelControl.getVideoIds(ht.continentId)
                    RemainingTime(ht.continentId,
                                  ht.count,
                                  ht.stars,
                                  calculateRemainingTime(ht.timeStamp, starExpiration))
                  }
                  .toList
              )
          case None =>
            out ! RemainingTimeList(tokenId, List.empty[RemainingTime])
        }
    case GetHintData(path) =>
      for {
        hintOpt <- videoHintDAO.getHint(tokenId, path)
        videoIds = levelControl.getVideoIds(path)
      } yield
        hintOpt match {
          case Some(hintTaken) =>
            val remainingTime = calculateRemainingTime(hintTaken.timeStamp, starExpiration)
            (if (remainingTime == 0) {
               for {
                 reset <- videoHintDAO.reset(tokenId, path)
               } yield RemainingTime(reset.continentId, reset.count, calculateStars(3, videoIds.length), remainingTime)
             } else {
               FastFuture.successful(
                 RemainingTime(hintTaken.continentId, hintTaken.count, hintTaken.stars, remainingTime)
               )
             }).map(out ! _)
          case None => out ! NoHints()
        }
    case actorFailed: ActorFailed => out ! actorFailed
  }
}

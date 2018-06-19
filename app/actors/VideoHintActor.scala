package actors

import java.time.Instant

import actors.messages.ActorFailed
import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.Inject
import model.models.{HintTaken, PlayerToken, Stats, VideoHint}
import model.{PlayerTokenDAO, VideoHintDAO}
import play.api.Environment
import play.api.libs.ws.WSClient
import types.{LevelName, StepName, TokenId, URL}

import scala.concurrent.ExecutionContext.Implicits.global

object VideoHintActor {
  final case class GetVideo(tokenId: TokenId)

  final case class GetPlayerToken(tokenId: TokenId)

  final case class GetVideoHint(playerToken: PlayerToken, stats: Stats)

  final case class UpdateExistingVideo(playerToken: PlayerToken,
                                       videoHint: VideoHint,
                                       hintTaken: HintTaken,
                                       stats: Stats,
                                       videoIds: List[String])

  final case class InsertNewVideo(playerToken: PlayerToken, videoHint: VideoHint, stats: Stats, videoIds: List[String])

  final case class InsertNewVideoRecord(playerToken: PlayerToken, stats: Stats, videoIds: List[String])

  final case class GetHintsTaken(tokenId: TokenId)

  final case class VideoCompiled(tokenId: TokenId, videoUrl: URL, stats: Stats)

  final case class Hints(videoHint: VideoHint)

  def generateTimestamp: Long = Instant.now.getEpochSecond

  def updatePlayerToken(playerToken: PlayerToken, level: LevelName, step: StepName, videoCount: Int): PlayerToken = {
    val stats = playerToken.stats.get
    val stat = stats.levels(level)(step)
    val starRatio = Math.ceil(3.toDouble / videoCount).toInt
    val stars = Math.max(0, stat.stars - starRatio)
    val updatedStat = stat.copy(stars = stars)
    val updatedStats = stats.copy(levels = Map(level -> (stats.levels(level) + (step -> stat, step -> updatedStat))))
    playerToken.copy(stats = Some(updatedStats))
  }

  def updateVideoHint(videoHint: VideoHint,
                      hintCount: Int,
                      videoIds: List[String],
                      level: LevelName,
                      step: StepName): VideoHint = {
    val updatedVideoHint = videoHint.videosWatched.map {
      case ht if ht.level == level && ht.step == step =>
        ht.copy(timeStamp = generateTimestamp, hintCount = hintCount)
      case ht => ht
    }
    videoHint.copy(videosWatched = updatedVideoHint)
  }

  def props(out: ActorRef,
            playerTokenDAO: PlayerTokenDAO,
            videoHintDAO: VideoHintDAO,
            ws: WSClient,
            environment: Environment) =
    Props(new VideoHintActor(out, playerTokenDAO, videoHintDAO, ws, environment))
}

class VideoHintActor @Inject()(out: ActorRef,
                               playerTokenDAO: PlayerTokenDAO,
                               videoHintDAO: VideoHintDAO,
                               ws: WSClient,
                               environment: Environment)
    extends Actor {
  import VideoHintActor._

  val levelGenerator: LevelGenerator = new LevelGenerator(environment)

  /*
   * For future implementation with youtube api...
   * */
  private val CHANNEL_ID: String = "UCCRrOAkZKCMB3wEIGEmdMEQ"
  private val KEY: String = "AIzaSyDY3RSvLmw2uBc3ZarYujOfHtqgJ8n6mPI"
  private val YOUTUBE_URL: URL = "https://www.googleapis.com/youtube/v3/"

  private def embedURL(videoId: String): URL = s"https://www.youtube.com/embed/$videoId"

  override def receive: Receive = {
    case GetVideo(tokenId) =>
      self ! GetPlayerToken(tokenId)
    case GetPlayerToken(tokenId) =>
      for {
        playerTokenOpt <- playerTokenDAO.getToken(tokenId)
      } yield
        playerTokenOpt match {
          case Some(playerToken) =>
            for {
              stats <- playerToken.stats
            } yield self ! GetVideoHint(playerToken, stats)
          case None => out ! ActorFailed(s"Unable to locate tokenId $tokenId")
        }
    case GetVideoHint(playerToken, stats) =>
      for {
        videoHintOpt <- videoHintDAO.getHints(playerToken.token_id)
        videoIds = levelGenerator.getRawStepData(stats.level, stats.step) match {
          case Some(rawStepData) => rawStepData.videoHints
          case None => List.empty[String]
        }
      } yield
        videoHintOpt match {
          // If videoHint field exists in VideoHint table
          case Some(videoHint) =>
            // Test if level/step exist in videos watched
            videoHint.videosWatched.find(v => v.level == stats.level && v.step == stats.step) match {
              case Some(hintTaken) =>
                self ! UpdateExistingVideo(playerToken, videoHint, hintTaken, stats, videoIds)
              case None => self ! InsertNewVideo(playerToken, videoHint, stats, videoIds)
            }
          // if not in database insert new record into database
          case None => self ! InsertNewVideoRecord(playerToken, stats, videoIds)
        }
    case UpdateExistingVideo(playerToken, videoHint, hintTaken, stats, videoIds) =>
      // Increment hint count
      val hintCount =
        if (hintTaken.hintCount < videoIds.length) hintTaken.hintCount + 1 else videoIds.length
      // Handle updating videoHint
      val updatedVideoHint = updateVideoHint(videoHint, hintCount, videoIds, stats.level, stats.step)
      videoHintDAO.update(updatedVideoHint)
      // Handle updating player token
      val updatedPlayerToken = updatePlayerToken(playerToken, stats.level, stats.step, videoIds.length)
      playerTokenDAO.updateToken(updatedPlayerToken)
      // respond to client
      val videoUrl = embedURL(videoIds(hintCount - 1))
      out ! VideoCompiled(playerToken.token_id, videoUrl, updatedPlayerToken.stats.get)
    case InsertNewVideo(playerToken, videoHint, stats, videoIds) =>
      // Initialize hint count
      val hintCount = 1
      // Handle creating hintTaken
      val hintTaken = HintTaken(stats.level, stats.step, generateTimestamp, hintCount)
      // Handle updating videoHint
      videoHintDAO.update(videoHint.copy(videosWatched = hintTaken :: videoHint.videosWatched))
      // Handle updating playerToken
      val updatedPlayerToken = updatePlayerToken(playerToken, stats.level, stats.step, videoIds.length)
      playerTokenDAO.updateToken(updatedPlayerToken)
      // respond to client
      val videoUrl = embedURL(videoIds(hintCount - 1))
      out ! VideoCompiled(playerToken.token_id, videoUrl, updatedPlayerToken.stats.get)
    case InsertNewVideoRecord(playerToken, stats, videoIds) =>
      // Initialize hint count
      val hintCount = 1
      // Handle creating hintTaken
      val hintTaken = HintTaken(stats.level, stats.step, generateTimestamp, hintCount)
      // Handle creating videoHint record
      val videoHint = VideoHint(playerToken.token_id, List(hintTaken))
      // Handle inserting video hint into db
      videoHintDAO.insert(videoHint)
      // Handle updating playerToken
      val updatedPlayerToken = updatePlayerToken(playerToken, stats.level, stats.step, videoIds.length)
      playerTokenDAO.updateToken(updatedPlayerToken)
      // respond to client
      val videoUrl = embedURL(videoIds(hintCount - 1))
      out ! VideoCompiled(playerToken.token_id, videoUrl, updatedPlayerToken.stats.get)
    case GetHintsTaken(tokenId) =>
      videoHintDAO.getHints(tokenId) map { hints =>
        out ! Hints(hints.getOrElse(VideoHint(tokenId, List.empty[HintTaken])))
      }
    case actorFailed: ActorFailed => out ! actorFailed
  }
}

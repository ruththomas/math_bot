package actors

import java.time.Instant

import actors.messages.ActorFailed
import akka.actor.{Actor, ActorRef, Props}
import com.google.inject.Inject
import model.models.{HintTaken, HintsTaken, PlayerToken, Stats}
import model.{PlayerTokenDAO, VideoHintDAO}
import play.api.Environment
import play.api.libs.json.{Json, OFormat}
import play.api.libs.ws.WSClient
import types.{LevelName, StepName, TokenId, URL}

import scala.concurrent.ExecutionContext.Implicits.global

object VideoHintActor {
  final case class GetVideo(tokenId: TokenId)

  final case class GetPlayerToken(tokenId: TokenId)

  final case class GetVideoHint(playerToken: PlayerToken, stats: Stats)

  final case class UpdateExistingVideo(playerToken: PlayerToken,
                                       videoHint: HintsTaken,
                                       hintTaken: HintTaken,
                                       stats: Stats,
                                       videoIds: List[String])

  final case class InsertNewVideo(playerToken: PlayerToken, videoHint: HintsTaken, stats: Stats, videoIds: List[String])

  final case class InsertNewVideoRecord(playerToken: PlayerToken, stats: Stats, videoIds: List[String])

  final case class GetHintsTaken(tokenId: TokenId)

  final case class HintPrepared(tokenId: TokenId, videoUrl: URL, stats: Stats)

  final case class RemainingTimeList(tokenId: TokenId, list: List[RemainingTime])

  final case class GetRemainingTime(tokenId: TokenId, level: LevelName, step: StepName)

  object RemainingTime {
    implicit val remainingTimeFormat: OFormat[RemainingTime] = Json.format[RemainingTime]
  }

  final case class RemainingTime(tokenId: TokenId, level: LevelName, step: StepName, time: Long)

  final case class ResetStars(tokenId: TokenId, hintsTaken: HintsTaken, level: LevelName, step: StepName)

  def generateTimestamp: Long = Instant.now.getEpochSecond

  def updatePlayerToken(playerToken: PlayerToken,
                        level: LevelName,
                        step: StepName,
                        videoCount: Int,
                        setStars: Option[Int]): PlayerToken = {
    val stats = playerToken.stats.get
    val stat = stats.levels(level)(step)
    val starRatio = Math.ceil(3.toDouble / videoCount).toInt
    val stars = setStars.getOrElse(Math.max(0, stat.stars - starRatio))
    val updatedStat = stat.copy(stars = stars)
    val updatedStats = stats.copy(levels = stats.levels.map {
      case l if l._1 == level =>
        l._1 -> l._2.map {
          case s if s._1 == step =>
            s._1 -> updatedStat
          case s => s
        }
      case l => l
    })
    playerToken.copy(stats = Some(updatedStats))
  }

  def updateHintsTaken(videoHint: HintsTaken,
                       hintCount: Int,
                       videoIds: List[String],
                       level: LevelName,
                       step: StepName): HintsTaken = {
    val updatedVideoHint = videoHint.videosWatched.map {
      case ht if ht.level == level && ht.step == step =>
        ht.copy(timeStamp = generateTimestamp, hintCount = hintCount)
      case ht => ht
    }
    videoHint.copy(videosWatched = updatedVideoHint)
  }

  private val starExpiration = 3600 // bump up to 3600

  def calculateRemainingTime(timeStamp: Long): Long = {
    val expiredTime = timeStamp + starExpiration
    val timeNow = generateTimestamp
    val remainingTime = expiredTime - timeNow

    if (remainingTime <= 0) {
      0
    } else {
      remainingTime / 60
    }
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
  private val KEY: String = sys.env.getOrElse("MB_YOUTUBE_KEY", "none")
  private val YOUTUBE_URL: URL = "https://www.googleapis.com/youtube/v3/"

  private def embedURL(videoId: String): URL = s"https://www.youtube.com/embed/$videoId"

  override def receive: Receive = {
    /*
     * GetVideo - entry point for getting a video
     * */
    case GetVideo(tokenId) =>
      self ! GetPlayerToken(tokenId)
    /*
     * GetPlayerToken - gets player token from db
     * */
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
    /*
     * # GetVideoHint - gets video hint from db and controls traffic
     * If video hint found in db - videos already watched for game
     * ---- If level/step found - video watched for level/step - continue to UpdateExistingVideo
     * ---- If level/step not found - video never watched for level/step - continue to InsertNewVideo
     * If video hint not found - no videos ever watched for game - continue to InsertNewVideoRecord
     * */
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
          case Some(hintsTaken) =>
            // Test if level/step exist in videos watched
            hintsTaken.videosWatched.find(v => v.level == stats.level && v.step == stats.step) match {
              case Some(hintTaken) =>
                self ! UpdateExistingVideo(playerToken, hintsTaken, hintTaken, stats, videoIds)
              case None => self ! InsertNewVideo(playerToken, hintsTaken, stats, videoIds)
            }
          // if not in database insert new record into database
          case None => self ! InsertNewVideoRecord(playerToken, stats, videoIds)
        }
    /*
     * UpdateExistingVideo - if video has already been viewed for this level/step
     * */
    case UpdateExistingVideo(playerToken, hintsTaken, hintTaken, stats, videoIds) =>
      // Increment hint count
      val hintCount =
        if (hintTaken.hintCount < videoIds.length) hintTaken.hintCount + 1 else videoIds.length
      // Handle updating hintsTaken
      val updatedHintsTaken = updateHintsTaken(hintsTaken, hintCount, videoIds, stats.level, stats.step)
      videoHintDAO.update(updatedHintsTaken)
      // Handle updating player token
      val updatedPlayerToken = updatePlayerToken(playerToken, stats.level, stats.step, videoIds.length, None)
      playerTokenDAO.updateToken(updatedPlayerToken)
      // respond to client
      val videoUrl = embedURL(videoIds(hintCount - 1))
      out ! HintPrepared(playerToken.token_id, videoUrl, updatedPlayerToken.stats.get)
    /*
     * InsertNewVideo - if video has never been viewed for this level/step
     * */
    case InsertNewVideo(playerToken, hintsTaken, stats, videoIds) =>
      // Initialize hint count
      val hintCount = 1
      // Handle creating hintTaken
      val hintTaken = HintTaken(stats.level, stats.step, generateTimestamp, hintCount)
      // Handle updating hintsTaken
      val updatedHintsTaken = hintsTaken.copy(videosWatched = hintTaken :: hintsTaken.videosWatched)
      videoHintDAO.update(updatedHintsTaken)
      // Handle updating playerToken
      val updatedPlayerToken = updatePlayerToken(playerToken, stats.level, stats.step, videoIds.length, None)
      playerTokenDAO.updateToken(updatedPlayerToken)
      // respond to client
      val videoUrl = embedURL(videoIds(hintCount - 1))
      out ! HintPrepared(playerToken.token_id, videoUrl, updatedPlayerToken.stats.get)
    /*
     * InsertNewVideoRecord - if no videos have ever been watched during game
     * */
    case InsertNewVideoRecord(playerToken, stats, videoIds) =>
      // Initialize hint count
      val hintCount = 1
      // Handle creating hintTaken
      val hintTaken = HintTaken(stats.level, stats.step, generateTimestamp, hintCount)
      // Handle creating videoHint record
      val videoHint = HintsTaken(playerToken.token_id, List(hintTaken))
      // Handle inserting video hint into db
      videoHintDAO.insert(videoHint)
      // Handle updating playerToken
      val updatedPlayerToken = updatePlayerToken(playerToken, stats.level, stats.step, videoIds.length, None)
      playerTokenDAO.updateToken(updatedPlayerToken)
      // respond to client
      val videoUrl = embedURL(videoIds(hintCount - 1))
      out ! HintPrepared(playerToken.token_id, videoUrl, updatedPlayerToken.stats.get)
    /*
     * GetHintsTaken - returns a list of hints taken with a remaining time in minutes
     * used for initial rendering
     * */
    case GetHintsTaken(tokenId) =>
      videoHintDAO.getHints(tokenId) map {
        case Some(hintsTaken) =>
          out ! RemainingTimeList(
            tokenId,
            hintsTaken.videosWatched.map { ht =>
              val remainingTime = calculateRemainingTime(ht.timeStamp)
              RemainingTime(tokenId, ht.level, ht.step, remainingTime)
            }
          )
        case None => RemainingTimeList(tokenId, List.empty[RemainingTime])
      }
    /*
     * GetRemainingTime - gets new remaining time
     * Used for new timers as well as for resetting database when time is expired
     * Client calls this when video is clicked
     * Client calls this again when timer reaches zero in order to reset database
     * */
    case GetRemainingTime(tokenId, level, step) =>
      videoHintDAO.getHints(tokenId) map {
        case Some(hintsTaken) =>
          hintsTaken.videosWatched.find(ht => ht.level == level && ht.step == step) match {
            case Some(hintTaken) =>
              // get remaining time in minutes
              val remainingTime = calculateRemainingTime(hintTaken.timeStamp)
              // if remaining time is zero reset stars and hints taken
              if (remainingTime == 0) {
                self ! ResetStars(tokenId, hintsTaken, level, step)
                // else respond with remaining time
              } else {
                out ! RemainingTime(tokenId, level, step, remainingTime)
              }
            case None => out ! RemainingTime(tokenId, level, step, 0)
          }
        case None => out ! RemainingTime(tokenId, level, step, 0)
      }
    /*
     * ResetStars - resets stars in player token and removes level/step from video hint
     * Responds to client with remaining time of 0
     * The client will call for updated player token (PlayerTokenController) if response is 0
     * */
    case ResetStars(tokenId, hintsTaken, level, step) =>
      // get player token
      playerTokenDAO.getToken(tokenId) map {
        case Some(playerToken) =>
          // update player token stars
          playerTokenDAO.updateToken(updatePlayerToken(playerToken, level, step, 0, Some(3)))
          videoHintDAO.update(
            hintsTaken.copy(
              videosWatched = hintsTaken.videosWatched.filterNot(ht => ht.level == level && ht.step == step)
            )
          )
          out ! RemainingTime(tokenId, playerToken.stats.get.level, playerToken.stats.get.step, 0)
        case None => self ! ActorFailed("Cannot locate player token.")
      }
    case actorFailed: ActorFailed => out ! actorFailed
  }
}

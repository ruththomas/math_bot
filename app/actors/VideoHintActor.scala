package actors

import java.time.Instant

import actors.messages.ActorFailed
import akka.actor.{Actor, ActorRef, Props}
import akka.util.Timeout
import com.google.inject.Inject
import model.{PlayerTokenDAO, VideoHintDAO}
import model.models.{HintTaken, PlayerToken, Stats, VideoHint}
import play.api.Environment
import play.api.libs.ws.WSClient
import types.{LevelName, StepName, TokenId, URL}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object VideoHintActor {
  final case class GetVideo(tokenId: TokenId, level: LevelName, step: StepName)

  final case class GetPlayerToken(tokenId: TokenId, level: LevelName, step: StepName)

  final case class GetVideoHint(playerToken: PlayerToken, level: LevelName, step: StepName)

  final case class UpdateExistingVideo(playerToken: PlayerToken,
                                       videoHint: VideoHint,
                                       hintTaken: HintTaken,
                                       level: LevelName,
                                       step: StepName,
                                       videoIds: List[String])

  final case class InsertNewVideo(playerToken: PlayerToken,
                                  videoHint: VideoHint,
                                  level: LevelName,
                                  step: StepName,
                                  videoIds: List[String])

  final case class InsertNewVideoRecord(playerToken: PlayerToken,
                                        level: LevelName,
                                        step: StepName,
                                        videoIds: List[String])

  final case class VideoCompiled(tokenId: TokenId, videoUrl: URL, stats: Stats)

  def generateTimestamp: Long = Instant.now.getEpochSecond

  def updatePlayerToken(playerToken: PlayerToken, level: LevelName, step: StepName, hintCount: Int): PlayerToken = {
    val stats = playerToken.stats.get
    val stat = stats.levels(level)(step)
    val updatedStat = stat.copy(stars = if (stat.stars > 0) stat.stars - 1 else 0)
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
    case GetVideo(tokenId, level, step) =>
      self ! GetPlayerToken(tokenId, level, step)
    case GetPlayerToken(tokenId, level, step) =>
      for {
        playerTokenOpt <- playerTokenDAO.getToken(tokenId)
      } yield
        playerTokenOpt match {
          case Some(playerToken) => self ! GetVideoHint(playerToken, level, step)
          case None => out ! ActorFailed(s"Unable to locate tokenId $tokenId")
        }
    case GetVideoHint(playerToken, level, step) =>
      for {
        videoHintOpt <- videoHintDAO.getHints(playerToken.token_id)
        videoIds = levelGenerator.getRawStepData(level, step) match {
          case Some(rawStepData) => rawStepData.videoHints
          case None => List.empty[String]
        }
      } yield
        videoHintOpt match {
          // If videoHint field exists in VideoHint table
          case Some(videoHint) =>
            // Test if level/step exist in videos watched
            videoHint.videosWatched.find(v => v.level == level && v.step == step) match {
              case Some(hintTaken) =>
                self ! UpdateExistingVideo(playerToken, videoHint, hintTaken, level, step, videoIds)
              case None => self ! InsertNewVideo(playerToken, videoHint, level, step, videoIds)
            }
          // if not in database insert new record into database
          case None => self ! InsertNewVideoRecord(playerToken, level, step, videoIds)
        }
    case UpdateExistingVideo(playerToken, videoHint, hintTaken, level, step, videoIds) =>
      // Increment hint count
      val hintCount =
        if (hintTaken.hintCount < videoIds.length) hintTaken.hintCount + 1 else videoIds.length
      // Handle updating videoHint
      val updatedVideoHint = updateVideoHint(videoHint, hintCount, videoIds, level, step)
      videoHintDAO.update(updatedVideoHint)
      // Handle updating player token
      val updatedPlayerToken = updatePlayerToken(playerToken, level, step, hintCount)
      playerTokenDAO.updateToken(updatedPlayerToken)
      // respond to client
      val videoUrl = embedURL(videoIds(hintCount - 1))
      out ! VideoCompiled(playerToken.token_id, videoUrl, updatedPlayerToken.stats.get)
    case InsertNewVideo(playerToken, videoHint, level, step, videoIds) =>
      // Initialize hint count
      val hintCount = 1
      // Handle creating hintTaken
      val hintTaken = HintTaken(level, step, generateTimestamp, hintCount)
      // Handle updating videoHint
      videoHintDAO.update(videoHint.copy(videosWatched = hintTaken :: videoHint.videosWatched))
      // Handle updating playerToken
      val updatedPlayerToken = updatePlayerToken(playerToken, level, step, hintCount)
      playerTokenDAO.updateToken(updatedPlayerToken)
      // respond to client
      val videoUrl = embedURL(videoIds(hintCount - 1))
      out ! VideoCompiled(playerToken.token_id, videoUrl, updatedPlayerToken.stats.get)
    case InsertNewVideoRecord(playerToken, level, step, videoIds) =>
      // Initialize hint count
      val hintCount = 1
      // Handle creating hintTaken
      val hintTaken = HintTaken(level, step, generateTimestamp, hintCount)
      // Handle creating videoHint record
      val videoHint = VideoHint(playerToken.token_id, List(hintTaken))
      // Handle inserting video hint into db
      videoHintDAO.insert(videoHint)
      // Handle updating playerToken
      val updatedPlayerToken = updatePlayerToken(playerToken, level, step, hintCount)
      playerTokenDAO.updateToken(updatedPlayerToken)
      // respond to client
      val videoUrl = embedURL(videoIds(hintCount - 1))
      out ! VideoCompiled(playerToken.token_id, videoUrl, updatedPlayerToken.stats.get)
    case actorFailed: ActorFailed => out ! actorFailed
  }
}

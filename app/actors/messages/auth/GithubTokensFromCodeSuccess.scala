package actors.messages.auth

import models.GithubTokens
import utils.SecureIdentifier

case class GithubTokensFromCodeSuccess(sessionId : SecureIdentifier, tokens : GithubTokens)

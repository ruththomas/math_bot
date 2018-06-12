package utils

import akka.http.scaladsl.model.Uri

case class CompilerConfiguration(maxProgramSteps : Int)

case class WebClientConfig(signinSuccess : Uri, signinFail : Uri)
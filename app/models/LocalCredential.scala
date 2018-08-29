package models

import utils.SecureIdentifier

case class LocalCredential(accountId: SecureIdentifier, legacyAuthId : Option[String], username : String, salt: Array[Byte], hash: Array[Byte], iterations : Int, blocksize : Int)

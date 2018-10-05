package models

import utils.SecureIdentifier

case class LocalCredential(accountId: SecureIdentifier,
                           legacyAuthId: Option[String],
                           username: String,
                           salt: SecureIdentifier,
                           hash: Array[Byte],
                           iterations: Int,
                           blockSize: Int,
                           hashSize: Int,
                           admin: Option[Boolean] = None)

package models

case class LocalAuth(email: String, hashed: Seq[Byte], nounce : Seq[Byte])

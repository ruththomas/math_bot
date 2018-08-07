package daos

import models.JwtToken
import utils.SecureIdentifier

class SessionCache {

  private val synchronizedMap = new java.util.concurrent.ConcurrentHashMap[SecureIdentifier, Option[JwtToken]]()

  def get(key : SecureIdentifier) : Option[Option[JwtToken]] =
    if(synchronizedMap.containsKey(key)) Some(synchronizedMap.get(key)) else None

  def put(key : SecureIdentifier, value : Option[JwtToken]) : Option[JwtToken] = {
    synchronizedMap.put(key, value)
    value
  }

  def createOrUpdate(key : SecureIdentifier, updater : Option[Option[JwtToken]] => Option[JwtToken]) : Option[JwtToken] =
    put(key, updater(get(key)))

  def invalidate(key : SecureIdentifier) : Unit = {
    synchronizedMap.remove(key)
  }
}

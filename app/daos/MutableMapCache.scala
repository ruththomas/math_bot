package daos

import com.google.inject.Inject
import dataentry.caches.KeyValueCache
import models.JwtToken
import utils.SecureIdentifier

import scala.concurrent.Future

class MutableMapCache @Inject() extends KeyValueCache[SecureIdentifier, Option[JwtToken]] {

  private val synchronizedMap = new java.util.concurrent.ConcurrentHashMap[SecureIdentifier, Option[JwtToken]]()

  override def getAsync(key : SecureIdentifier) : Future[Option[Option[JwtToken]]] = ???

  override def putAsync(key : SecureIdentifier, value : Option[JwtToken]) : Future[Option[JwtToken]] = ???

  override def createOrUpdateAsync(key : SecureIdentifier, updater : Option[Option[JwtToken]] => Option[JwtToken]) : Future[Option[JwtToken]] = ???

  override def get(key : SecureIdentifier) : Option[Option[JwtToken]] = if(synchronizedMap.contains(key)) Some(synchronizedMap.get(key)) else None

  override def put(key : SecureIdentifier, value : Option[JwtToken]) : Option[JwtToken] = {
    synchronizedMap.put(key, value)
    value
  }

  override def createOrUpdate(key : SecureIdentifier, updater : Option[Option[JwtToken]] => Option[JwtToken]) : Option[JwtToken] =
    put(key, updater(get(key)))

  override def invalidate(key : SecureIdentifier) : Unit = {
    synchronizedMap.remove(key)
  }
}

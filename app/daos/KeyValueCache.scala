package dataentry.caches

import scala.concurrent.Future

trait KeyValueCache[Key <: AnyRef, Value] {
  def getAsync(key : Key) : Future[Option[Value]]
  def putAsync(key : Key, value : Value) : Future[Value]
  def createOrUpdateAsync(key : Key, updater : Option[Value] => Value) : Future[Value]
  def get(key : Key) : Option[Value]
  def put(key : Key, value : Value) : Value
  def createOrUpdate(key : Key, updater : Option[Value] => Value) : Value
  def invalidate(key : Key) : Unit
}

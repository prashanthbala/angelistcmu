package controllers.common

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 11/3/12
 * Time: 5:40 AM
 * To change this template use File | Settings | File Templates.
 */
class MongoClient(hostConfig: String, portConfig: Option[String] = None) {
  /*
   * automatically serialize/deserialize JodaTime
   * saved to MongoDB as proper BSON Dates, and on retrieval/deserialization all BSON Dates will be returned as Joda DateTime
   */
  RegisterJodaTimeConversionHelpers()

  val host = Configuration.getString(hostConfig)
  val port = portConfig match {
    case Some(_portConfig) => Configuration.getInt(_portConfig)
    case None => 27017
  }
  val mongoConn = MongoConnection(host, port)


  def withCollection[ResultType] (db: String) (collectionName: String) (f: MongoCollection => ResultType) : ResultType = {
    val collection = mongoConn(db)(collectionName)
    f(collection)
  }

  @Deprecated
  def withConnection[ResultType](f: MongoConnection => ResultType): ResultType = {
    f(mongoConn)
  }
}

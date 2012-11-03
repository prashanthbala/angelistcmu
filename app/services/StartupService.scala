package services

import models._
import controllers.common.MongoClient
import com.mongodb.casbah.Imports._
import services.Common._
import com.novus.salat._
import services.when_necessary_and_changeId._
import controllers.common.MongoClient

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 11/3/12
 * Time: 7:27 AM
 * To change this template use File | Settings | File Templates.
 */
class StartupService {
  private implicit def StartupToMongoFormatter(startup: Startup): DBObject = {
    grater[Startup].asDBObject(startup)
  }

  private implicit def MongoToStartupFormatter(startup: DBObject): Startup = {
    grater[Startup].asObject(startup).copy(id = startup.getAs[ObjectId]("_id").get)
  }

  private implicit def AssociationToMongoFormatter(association: Association): DBObject = {
    grater[Association].asDBObject(association)
  }

  private implicit def MongoToAssociationFormatter(association: DBObject): Association = {
    grater[Association].asObject(association)
  }

  val mongo = new MongoClient("mongo.host")
  lazy val startupCollection = mongo.withCollection[MongoCollection]("profiles")("users") {x => x}
  lazy val associationCollection = mongo.withCollection[MongoCollection]("graphs")("UserNStartup") {x => x}

  lazy val userService = new UserService
  lazy val startupService = new StartupService

  def getStartups(): List[Startup] = {
    startupCollection.find() map {MongoToStartupFormatter(_)} toList
  }

  def getStartupById(userId: String): Option[Startup] = {
    startupCollection.findOneByID(userId) map {MongoToStartupFormatter(_)}
  }

  def createStartup(startup: StartupJson) = mongoServiceCall {
      val updatedStartup: Startup = _convertFromStartupJsonToUserForInsert(startup)
      val startupMongo = StartupToMongoFormatter(updatedStartup)
      startupCollection.insert[Startup](startupMongo)
  }

  private def _convertFromStartupJsonToUserForInsert(startup: StartupJson): Startup = {
    Startup(
      id = new ObjectId,
      name = startup.name,
      description = startup.description
    )
  }


  def addUser(startupId: String, userId: String) = mongoServiceCall {
    associationCollection.insert(Association(
      startupId = startupId,
      userId = userId
    ))
  }

  def removeUser(startupId: String, userId: String) = mongoServiceCall {
    val findQuery = MongoDBObject("startup" -> startupId) ++ MongoDBObject("userId" -> userId)
    associationCollection.remove(findQuery)
  }

  def getUsersForStartup(startupId: String): List[User] = {
    associationCollection.find(MongoDBObject("startupId" -> startupId)) map {MongoToAssociationFormatter(_)} flatMap {association =>
      userService.getUserById(association.userId)
    } toList
  }

  def getStartupsForUser(userId: String): List[Startup] = {
    associationCollection.find(MongoDBObject("userId" -> userId)) map {MongoToAssociationFormatter(_)} flatMap {association =>
      startupService.getStartupById(association.startupId)
    } toList
  }
}

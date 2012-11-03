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
 * Time: 5:49 AM
 * To change this template use File | Settings | File Templates.
 */
class UserService {
  private implicit def UserToMongoFormatter(user: User): DBObject = {
    grater[User].asDBObject(user)
  }

  private implicit def MongoToUserFormatter(user: DBObject): User = {
    grater[User].asObject(user).copy(id = user.getAs[ObjectId]("_id").get)
  }

  val mongo = new MongoClient("mongo.host")
  lazy val userCollection = mongo.withCollection[MongoCollection]("user")("profiles") {x => x}

  def getUsers(): List[User] = {
     userCollection.find() map {MongoToUserFormatter(_)} toList
  }

  def createUser(user: UserJson) = {
    mongoServiceCall {
      val updatedUser: User = _convertFromUserJsonToUserForInsert(user)
      val userMongo = UserToMongoFormatter(updatedUser)
      userCollection.insert[User](updatedUser)
    }
  }

  private def _convertFromUserJsonToUserForInsert(user: UserJson): User = {
    User(
      id = new ObjectId,
      name = user.name
    )
  }
}

package models

import com.mongodb.casbah.Imports._
import scala.Some

/* representation used internally as well as in mongo */
case class User(
   id: ObjectId,
   name: String
) {
  def toUserJson: UserJson = {
    UserJson(
      id = Some(id.toString),
      name = name
    )
  }
}

/*  representation used in json parsing and sending json back since id has to be string and
	a bunch of fields are not required during inserts */
case class UserJson(
                     id: Option[String],
                     name: String
)




package models

import com.mongodb.casbah.Imports._

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 8/22/12
 * Time: 8:19 PM
 * To change this template use File | Settings | File Templates.
 */

case class Startup(
  id: ObjectId,
  name: String,
  description: String
)  {
  def toStartupJson: StartupJson = {
    StartupJson(
      id = Some(id.toString),
      name = name,
      description = description
    )
  }
}

case class StartupJson(
     id: Option[String],
     name: String,
     description: String
)


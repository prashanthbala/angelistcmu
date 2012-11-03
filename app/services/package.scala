package services

import com.mongodb.casbah.Imports._
import com.novus.salat._
import models.User
import com.novus.salat.Context
import com.novus.salat.StringTypeHintStrategy
import com.novus.salat.TypeHintFrequency

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 8/26/12
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */

package object when_necessary_and_changeId {
  implicit val ctx = new Context {
    val name = "When-Necessary-Context"
    override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary,
      typeHint = "_t")
  }

  /**
   * Models have their mongo persistence changes defined here
   */

  // Renaming the default _id to id for User
  ctx.registerPerClassKeyOverride(classOf[User], remapThis = "id", toThisInstead = "_id") //to limit it only to User class
}

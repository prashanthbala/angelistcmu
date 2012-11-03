package controllers.common

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 11/3/12
 * Time: 5:39 AM
 * To change this template use File | Settings | File Templates.
 */

object Configuration {

  lazy val root = play.Configuration.root

  def getString(s: String): String = {
    root getString(s)
  }
  def getInt(s: String): Int = {
    root getInt(s)
  }
  def getBoolean(s: String): Boolean = {
    root getBoolean(s)
  }
}


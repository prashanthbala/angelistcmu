package services

import com.mongodb.casbah.Imports._

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 11/3/12
 * Time: 5:49 AM
 * To change this template use File | Settings | File Templates.
 */
object Common {
  trait ServiceResult
  case object Success extends ServiceResult
  case class Failure(e: Exception) extends ServiceResult

  def mongoServiceCall(f: => WriteResult): ServiceResult = {
    val wr: WriteResult = f
    wr.getLastError.ok match {
      case true => Success
      case false => Failure(wr.getLastError.getException)
    }
  }
}



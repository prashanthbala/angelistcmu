package controllers.common

import services.Common.Failure
import play.api.mvc.{Result, Action, Controller}
import play.api.Logger
import com.codahale.jerkson.Json

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 11/3/12
 * Time: 5:48 AM
 * To change this template use File | Settings | File Templates.
 */

trait JsonController extends Controller with JsonUtil {

  def jsonGetApi[ResponseType](response: => ResponseType): Action[String] = {  //Action[AnyContent] -> for the original GET api
    Action(parse.tolerantText) {request  => //this is used so that some posts can get modelled as GETs where the request body is ignored
      val _response = response
      _response match {
        case Failure(e: Exception) => InternalServerError(e.toString)
        case _ =>
          val jsonResponse = serialize(_response)
          formatAsJsonResponse(jsonResponse)
      }
    }
  }

  def jsonPostApi[RequestType, ResponseType](response: RequestType => ResponseType) (implicit mf : scala.Predef.Manifest[RequestType]): Action[String] = {
    Action(parse.tolerantText) { request  =>
      val requestBody: String = request.body
      Logger debug ("Request Body Received is : " + requestBody)

      val requestObject: RequestType = Json.parse[RequestType](requestBody)
      val jsonResponse = serialize(response(requestObject))

      formatAsJsonResponse(jsonResponse)
    }
  }

  def formatAsJsonResponse(jsonResponse: String): Result = {
    Ok(jsonResponse).as("application/json")
  }
}

trait JsonUtil {
  class JSONSerializationException(message: String) extends IllegalArgumentException

  def serialize[A](response: A) = {
    Json.canSerialize[A](Manifest.classType(response.getClass)) match {
      case true => Json.generate[A](response)
      case false => throw new JSONSerializationException("Not a valid serlizable object")
    }
  }
}

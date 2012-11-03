package controllers

import common.JsonController
import services.{StartupService, UserService}
import models.{StartupJson, UserJson}
import services.Common.ServiceResult
import play.api.mvc.Action

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 11/3/12
 * Time: 7:06 AM
 * To change this template use File | Settings | File Templates.
 */
class Startups extends JsonController {
    lazy val startupService = new StartupService

    def getStartups() = Action {
      Ok(views.html.startuplisting(startupService.getStartups))
    }

    def createStartup() = jsonPostApi[StartupJson, ServiceResult] {
      startupService.createStartup(_)
    }

    def addUser(startupId: String, userId: String) = jsonPostApi[String, ServiceResult]{_ =>
      startupService.addUser(startupId, userId)
    }

    def removeUser(startupId: String, userId: String) = jsonPostApi[String, ServiceResult]{_ =>
      startupService.removeUser(startupId, userId)
    }

    def getUsersForStartup(startupId: String) = jsonGetApi {
      startupService.getUsersForStartup(startupId)
    }

    def getStartupsForUser(userId: String) = jsonGetApi {
      startupService.getStartupsForUser(userId)
    }
}

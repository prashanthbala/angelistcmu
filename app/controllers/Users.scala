package controllers

import common.JsonController
import services.UserService
import models.UserJson
import services.Common.ServiceResult

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 11/3/12
 * Time: 5:37 AM
 * To change this template use File | Settings | File Templates.
 */

object Users extends JsonController {

    val userService = new UserService

    def getUsers() = {
      Ok(views.html.userlisting(userService.getUsers))
    }

    def createUser() = jsonPostApi[UserJson, ServiceResult] {
        userService.createUser(_)
    }
}
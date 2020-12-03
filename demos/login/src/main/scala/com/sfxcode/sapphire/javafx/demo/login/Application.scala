package com.sfxcode.sapphire.javafx.demo.login

import com.sfxcode.sapphire.javafx.application.BaseApplication
import com.sfxcode.sapphire.javafx.controller.BaseApplicationController
import com.sfxcode.sapphire.javafx.demo.login.controller.{LoginController, ProfileController}
import com.sfxcode.sapphire.javafx.demo.login.model.User
import com.sfxcode.sapphire.javafx.value.FXBean
import javafx.stage.Stage

object Application extends BaseApplication {

  override def width: Int = 500

  override def height: Int = 500

  override def title: String = "Login Demo"

  override def initStage(stage: Stage): Unit = {
    super.initStage(stage)
    stage.setResizable(false)
  }

  override val applicationController: BaseApplicationController = new LoginApplicationController
}

class LoginApplicationController extends BaseApplicationController {
  lazy val loginController: LoginController     = getController[LoginController]()
  lazy val profileController: ProfileController = getController[ProfileController]()

  var applicationUser: Option[FXBean[User]] = None

  def applicationDidLaunch() {
    println("start " + this)
    println(loginController)
    replaceSceneContent(loginController)
    showLogin()
  }

  def showLogin() {
    replaceSceneContent(loginController)
  }

  def showMain() {
    replaceSceneContent(profileController)
  }
}

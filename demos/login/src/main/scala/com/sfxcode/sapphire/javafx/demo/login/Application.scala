package com.sfxcode.sapphire.javafx.demo.login

import com.sfxcode.sapphire.javafx.application.SFXApplication
import com.sfxcode.sapphire.javafx.controller.SFXApplicationController
import com.sfxcode.sapphire.javafx.demo.login.controller.{ LoginController, ProfileController }
import com.sfxcode.sapphire.javafx.demo.login.model.User
import com.sfxcode.sapphire.javafx.value.SFXBean
import javafx.stage.Stage

object Application extends SFXApplication {

  override def width: Int = 500

  override def height: Int = 500

  override def title: String = "Login Demo"

  override def initStage(stage: Stage): Unit = {
    super.initStage(stage)
    stage.setResizable(false)
  }

  override val applicationController: SFXApplicationController = new LoginApplicationController
}

class LoginApplicationController extends SFXApplicationController {
  lazy val loginController: LoginController = getController[LoginController]()
  lazy val profileController: ProfileController = getController[ProfileController]()

  var applicationUser: Option[SFXBean[User]] = None

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

package com.sfxcode.sapphire.javafx.demo.login.controller

import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.sfxcode.sapphire.javafx.demo.login.LoginApplicationController
import com.sfxcode.sapphire.javafx.demo.login.model.UserDatabase
import com.sfxcode.sapphire.javafx.value.SFXBeanConversions
import javafx.fxml.FXML
import javafx.scene.control._

class LoginController extends SFXViewController with SFXBeanConversions {

  @FXML
  var email: TextField = _
  @FXML
  var password: PasswordField = _
  @FXML
  var loginButton: Button = _
  @FXML
  var errorMessage: Label = _

  override def didGainVisibility() {
    super.didGainVisibility()
    errorMessage.setText("")
    email.setText("admin@logindemo.com")
  }

  def actionLogin() {
    val user = UserDatabase.find(email.getText, password.getText)
    val authenticated = user.isDefined
    if (authenticated) {
      password.clear()
      applicationController().applicationUser = user
      applicationController().showMain()
    } else
      errorMessage.setText("Login Error")
  }

  def applicationController(): LoginApplicationController = registeredBean[LoginApplicationController].get

}

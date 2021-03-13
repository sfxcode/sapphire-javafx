package com.sfxcode.sapphire.javafx.demo.login.controller

import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.sfxcode.sapphire.javafx.demo.login.LoginApplicationController
import com.sfxcode.sapphire.javafx.demo.login.model.User
import com.sfxcode.sapphire.javafx.value._
import javafx.event.ActionEvent
import javafx.scene.control.CheckBox

class ProfileController extends SFXViewController {

  lazy val userAdapter: SFXBeanAdapter[User] = SFXBeanAdapter[User](this)

  override def didGainVisibility() {
    super.didGainVisibility()
    val bindings = SFXKeyBindings("email", "phone", "address", "subscribed")
    bindings.add("user", "User: ${_self.name()} Mailsize: (${_self.email().length()})")
    userAdapter.addBindings(bindings)

    userAdapter.set(applicationController().applicationUser.get)
  }

  def actionLogout(event: ActionEvent) {
    userAdapter.revert()
    userAdapter.unset()
    applicationController().applicationUser = None
    applicationController().showLogin()
  }

  def applicationController(): LoginApplicationController = registeredBean[LoginApplicationController].get

  def actionUpdate(event: ActionEvent) {
    debugUserData()
  }

  def debugUserData() {

    val maybeTextField = locateTextField("user")
    println(maybeTextField)

    val checkBoxOption = locate[CheckBox]("#subscribed")
    checkBoxOption.foreach(cb => println(cb.selectedProperty.get))

    println(applicationController().applicationUser.get.bean)
  }
}

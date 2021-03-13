package com.sfxcode.sapphire.javafx.demo.tutorial

import com.sfxcode.sapphire.javafx.application.SFXApplication
import com.sfxcode.sapphire.javafx.controller.SFXApplicationController

object Application extends SFXApplication {

  val applicationController: SFXApplicationController = new ApplicationController

  override def height: Int = 600

  override def width: Int = 800

  override def forceMaxWidth: Boolean = true

  override def forceMaxHeight: Boolean = true
}

package com.sfxcode.sapphire.javafx.demo.tutorial

import com.sfxcode.sapphire.javafx.application.SFXBaseApplication
import com.sfxcode.sapphire.javafx.controller.SFXBaseApplicationController

object Application extends SFXBaseApplication {

  val applicationController: SFXBaseApplicationController = new ApplicationController

  override def height: Int = 600

  override def width: Int = 800

  override def forceMaxWidth: Boolean = true

  override def forceMaxHeight: Boolean = true
}

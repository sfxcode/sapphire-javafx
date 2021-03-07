package com.sfxcode.sapphire.javafx.demo.tutorial

import com.sfxcode.sapphire.javafx.application.BaseApplication
import com.sfxcode.sapphire.javafx.controller.BaseApplicationController

object Application extends BaseApplication {

  val applicationController: BaseApplicationController = new ApplicationController

  override def height: Int = 600

  override def width: Int = 800

  override def forceMaxWidth: Boolean = true

  override def forceMaxHeight: Boolean = true
}

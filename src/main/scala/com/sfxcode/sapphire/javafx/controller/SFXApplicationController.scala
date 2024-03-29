package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.javafx.application.SFXApplicationEnvironment
import com.sun.javafx.css.StyleManager
import javafx.application.Platform
import javafx.stage.Stage

abstract class SFXApplicationController extends SFXWindowController {

  override def isMainWindow: Boolean = true

  def onApplicationStartup(stage: Stage): Unit = {
    applicationWillLaunch()
    setStage(stage)
    SFXApplicationEnvironment.setApplicationController(this)
    applicationDidLaunch()
  }

  def applicationWillLaunch(): Unit = {}

  def applicationDidLaunch(): Unit

  def reloadStyles(): Unit =
    StyleManager.getInstance().stylesheetContainerMap.clear()

  override def shutdown(): Unit = {
    super.shutdown()
    applicationWillStop()
  }

  def applicationWillStop(): Unit =
    logger.debug("exit in Progress")

  def exit(): Unit =
    Platform.exit()

}

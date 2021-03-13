package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.javafx.application.SFXApplicationEnvironment
import com.sun.javafx.css.StyleManager
import javafx.application.Platform
import javafx.stage.Stage

abstract class SFXBaseApplicationController extends SFXWindowController {

  override def isMainWindow: Boolean = true

  def onApplicationStartup(stage: Stage) {
    applicationWillLaunch()
    setStage(stage)
    SFXApplicationEnvironment.setApplicationController(this)
    applicationDidLaunch()
  }

  def applicationWillLaunch() {}

  def applicationDidLaunch()

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

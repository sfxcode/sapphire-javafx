package com.sfxcode.sapphire.javafx.application

import com.sfxcode.sapphire.javafx.controller.SFXApplicationController
import com.sfxcode.sapphire.javafx.stage.SFXStageSupport
import com.typesafe.scalalogging.LazyLogging
import javafx.application.Application

// #BaseApplication
abstract class SFXApplication extends SFXStageSupport with LazyLogging {
  val startTime: Long = System.currentTimeMillis()

  def main(args: Array[String]): Unit = {
    SFXApplicationEnvironment.setApplication(this)
    Application.launch(classOf[SFXJavaApplication], args: _*)
  }

  val applicationController: SFXApplicationController

  def applicationWillLaunch(): Unit = {}

  def applicationDidLaunch(): Unit =
    logger.info("Application Startup in %s ms".format(System.currentTimeMillis() - startTime))

  def applicationWillTerminate(): Unit = {}

}
// #BaseApplication

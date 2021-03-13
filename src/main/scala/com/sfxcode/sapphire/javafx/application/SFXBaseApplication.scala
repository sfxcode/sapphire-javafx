package com.sfxcode.sapphire.javafx.application

import com.sfxcode.sapphire.javafx.controller.SFXBaseApplicationController
import com.sfxcode.sapphire.javafx.stage.SFXStageSupport
import com.typesafe.scalalogging.LazyLogging
import javafx.application.Application

// #BaseApplication
abstract class SFXBaseApplication extends SFXStageSupport with LazyLogging {
  val startTime: Long = System.currentTimeMillis()

  def main(args: Array[String]): Unit = {
    SFXApplicationEnvironment.setApplication(this)
    Application.launch(classOf[SFXApplication], args: _*)
  }

  val applicationController: SFXBaseApplicationController

  def applicationWillLaunch(): Unit = {}

  def applicationDidLaunch(): Unit =
    logger.info("Application Startup in %s ms".format(System.currentTimeMillis() - startTime))

  def applicationWillTerminate(): Unit = {}

}
// #BaseApplication

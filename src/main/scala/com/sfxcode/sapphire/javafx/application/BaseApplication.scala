package com.sfxcode.sapphire.javafx.application

import com.sfxcode.sapphire.javafx.controller.BaseApplicationController
import com.sfxcode.sapphire.javafx.stage.StageSupport
import com.typesafe.scalalogging.LazyLogging
import javafx.application.Application

// #BaseApplication
abstract class BaseApplication extends StageSupport with LazyLogging {
  val startTime: Long = System.currentTimeMillis()

  def main(args: Array[String]): Unit = {
    ApplicationEnvironment.setApplication(this)
    Application.launch(classOf[FXApplication], args: _*)
  }

  val applicationController: BaseApplicationController

  def applicationWillLaunch(): Unit = {}

  def applicationDidLaunch(): Unit =
    logger.info("Application Startup in %s ms".format(System.currentTimeMillis() - startTime))

  def applicationWillTerminate(): Unit = {}

}
// #BaseApplication

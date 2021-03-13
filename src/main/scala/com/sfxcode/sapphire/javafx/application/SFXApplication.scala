package com.sfxcode.sapphire.javafx.application

import javafx.stage.Stage

class SFXApplication extends javafx.application.Application {
  var applicationStage: Stage = _

  def start(stage: javafx.stage.Stage): Unit = {
    SFXApplicationEnvironment.wrappedApplication = this
    val application = SFXApplicationEnvironment.application
    applicationStage = application.createDefaultStage()
    application.applicationWillLaunch()

    val windowController = application.applicationController
    windowController.onApplicationStartup(applicationStage)

    application.applicationDidLaunch()

    applicationStage.show()
  }

  override def stop(): Unit =
    SFXApplicationEnvironment.application.applicationWillTerminate()
}

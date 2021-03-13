package com.sfxcode.sapphire.javafx.concurrent.akka

import javafx.application.Platform

object SFXExecutorService extends SFXAbstractExecutorService {
  override def execute(command: Runnable): Unit = Platform.runLater(command)
}

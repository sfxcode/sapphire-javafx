package com.sfxcode.sapphire.javafx.concurrent.akka

import javafx.application.Platform

object JavaFXExecutorService extends GUIExecutorService {
  override def execute(command: Runnable) = Platform.runLater(command)
}

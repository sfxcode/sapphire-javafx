package com.sfxcode.sapphire.javafx.concurrent.akka

import javax.swing.SwingUtilities

object SwingExecutorService extends GUIExecutorService {
  override def execute(command: Runnable) = SwingUtilities.invokeLater(command)
}

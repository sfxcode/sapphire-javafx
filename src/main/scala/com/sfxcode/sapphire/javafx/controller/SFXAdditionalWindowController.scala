package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.javafx.stage.SFXStageSupport
import javafx.stage.Stage

abstract class SFXAdditionalWindowController extends SFXWindowController with SFXStageSupport {

  override def isMainWindow: Boolean = false

  def createStage(): Unit =
    if (stageProperty.isEmpty) {
      setStage(createDefaultStage())
    }

  def show(x: Double = 0.0, y: Double = 0.0): Unit =
    stageProperty.foreach {
      stage =>
        if (!stage.isShowing) {
          setStagePosition(stage, x, y)
          stage.show()
        }
    }

  def showAndWait(x: Double = 0.0, y: Double = 0.0): Unit =
    stageProperty.foreach {
      stage =>
        if (!stage.isShowing) {
          setStagePosition(stage, x, y)
          stage.showAndWait()
        }
    }

  def close(): Unit =
    stageProperty.foreach {
      stage =>
        if (stage.isShowing) {
          stage.close()
        }

    }

  private def setStagePosition(myStage: Stage, x: Double, y: Double): Unit = {
    if (x > 0) {
      myStage.setX(x)
    }
    if (y > 0) {
      myStage.setY(y)
    }
  }

}

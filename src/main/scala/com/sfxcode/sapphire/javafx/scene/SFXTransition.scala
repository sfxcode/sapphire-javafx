package com.sfxcode.sapphire.javafx.scene

import javafx.animation.{ Interpolator, KeyFrame, KeyValue, Timeline }
import javafx.scene.layout.Pane
import javafx.util.Duration

abstract class SFXTransition {
  def createTimeline(pane: Pane, oldRootPane: Pane): Timeline
}

case class SFXEaseInTransition(duration: Duration = Duration.seconds(1)) extends SFXTransition {

  def createTimeline(newRootPane: Pane, oldRootPane: Pane): Timeline = {
    val translateX = newRootPane.translateXProperty()
    translateX.set(oldRootPane.getWidth)
    val timeline = new Timeline
    val keyValue = new KeyValue(translateX, double2Double(0), Interpolator.EASE_IN)
    val keyFrame = new KeyFrame(duration, keyValue)
    timeline.getKeyFrames.add(keyFrame)
    timeline
  }
}

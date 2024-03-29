package com.sfxcode.sapphire.javafx.stage

import com.sfxcode.sapphire.javafx.Configuration
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.{ Modality, Stage, StageStyle }

trait SFXStageSupport extends Configuration {

  def createDefaultStage(): Stage = {
    val stage = new Stage()
    stage.setWidth(width)
    stage.setHeight(height)
    if (forceMinWidth)
      stage.setMinWidth(width)
    if (forceMinHeight)
      stage.setMinHeight(height)
    if (forceMaxWidth)
      stage.setMaxWidth(width)
    if (forceMaxHeight)
      stage.setMaxHeight(height)
    stage.setTitle(title)
    stage.initModality(modality)
    stage.initStyle(stageStyle)
    initStage(stage)
    stage
  }

  def initStage(stage: Stage): Unit = {}

  def stageStyle: StageStyle = StageStyle.DECORATED

  def modality: Modality = Modality.NONE

  def title: String = configStringValue("sapphire.javafx.defaultStage.title", "SFX Application")

  def width: Int = configIntValue("sapphire.javafx.defaultStage.width", 800)

  def height: Int = configIntValue("sapphire.javafx.defaultStage.height", 600)

  def forceMinWidth: Boolean = true

  def forceMinHeight: Boolean = true

  def forceMaxWidth: Boolean = false

  def forceMaxHeight: Boolean = false

}

package com.sfxcode.sapphire.javafx.stage

import com.sfxcode.sapphire.javafx.stage.StageValues._
import com.typesafe.scalalogging.LazyLogging

trait Stage extends LazyLogging {
  val StageKey = "SFX_SYSTEM_STAGE"

  def isDevelopment: Boolean =
    Development.toString == getStage

  def isStaging: Boolean =
    Staging.toString == getStage

  def isProduction: Boolean =
    Production.toString == getStage

  def isUnitTest: Boolean =
    UnitTest.toString == getStage

  def isSystemTest: Boolean =
    SystemTest.toString == getStage

  def getStage: String = {
    val stageProperty = System.getProperty(StageKey)
    if (stageProperty != null && stageProperty.nonEmpty) {
      stageProperty
    } else {
      Development.toString
    }
  }

  def setStage(stage: String): Unit = {
    System.setProperty(StageKey, stage)
    logger.debug("Stage changed to %s".format(stage))
  }

  def setUnitTest(): Unit = setStage(UnitTest.toString)

  def setSystemTest(): Unit = setStage(SystemTest.toString)

  def setDevelopment(): Unit = setStage(Development.toString)

  def setSProduction(): Unit = setStage(Production.toString)

  def setStaging(): Unit = setStage(Staging.toString)

}

package com.sfxcode.sapphire.javafx.demo.windows.controller

import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.typesafe.scalalogging.LazyLogging
import javafx.fxml.FXML
import javafx.scene.control.Label

class AdditionalViewController extends SFXViewController with LazyLogging {

  @FXML
  var windowLabel: Label = _

  override def startup(): Unit =
    logger.debug("class: " + this)

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibility()
    windowLabel.setText(windowController.get.name)
  }
}

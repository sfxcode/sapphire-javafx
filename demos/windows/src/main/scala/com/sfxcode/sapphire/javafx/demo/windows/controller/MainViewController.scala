package com.sfxcode.sapphire.javafx.demo.windows.controller

import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.sfxcode.sapphire.javafx.demo.windows.ApplicationController
import com.sfxcode.sapphire.javafx.value.SFXBean
import com.typesafe.scalalogging.LazyLogging
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Label

import scala.beans.BeanProperty

class MainViewController extends SFXViewController with LazyLogging {

  @FXML
  var windowLabel: Label = _

  @FXML
  var controllerLabel: Label = _

  @BeanProperty
  var name: String = "MainView"

  @BeanProperty
  var bean: SFXBean[SFXViewController] = SFXBean(this)

  override def startup(): Unit =
    logger.debug("class: " + this)

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    val expressionResult =
      evaluateExpressionOnObject[String](this, "result: [${_self.windowLabel().getText()}]").getOrElse("")
    controllerLabel.setText(expressionResult)
  }

  def applicationController: ApplicationController = windowController.get.asInstanceOf[ApplicationController]

  // #actionShowSecondWindow
  def actionShowSecondWindow(event: ActionEvent): Unit = {
    val x = applicationController.stage.getX + applicationController.stage.getWidth
    val y = applicationController.stage.getY
    applicationController.secondWindowController.show(x, y)
  }
  // #actionShowSecondWindow

  // #actionCloseSecondWindow
  def actionCloseSecondWindow(event: ActionEvent): Unit =
    applicationController.secondWindowController.close()
  // #actionCloseSecondWindow

  // #actionShowModalWindow
  def actionShowModalWindow(event: ActionEvent): Unit =
    applicationController.modalWindowController.showAndWait()
  // #actionShowModalWindow

}

package com.sfxcode.sapphire.javafx.demo.tutorial.controller.app

import com.sfxcode.sapphire.javafx.demo.tutorial.controller.base.AbstractViewController
import com.sfxcode.sapphire.javafx.scene.SFXEaseInTransition
import javafx.event.ActionEvent
import javafx.scene.control.Button

class NavigationController extends AbstractViewController {

  def actionToggleWorkspace(event: ActionEvent) {
    actionClickButton(event)
    val actualController = workspaceManager.actualController
    val barChartController = mainViewController.barChartController
    val workspaceController = mainViewController.workspaceController
    if (actualController == workspaceController)
      workspaceManager.updateWithTransition(barChartController)
    else
      workspaceManager.updateWithTransition(workspaceController)
  }

  def actionShowPersonController(event: ActionEvent): Unit = {
    actionClickButton(event)
    val personController = mainViewController.personController
    workspaceManager.updateWithTransition(personController)
  }

  def actionClickButton(event: ActionEvent) {
    logger.debug(event.toString)

    val button = event.getSource.asInstanceOf[Button]
    statusBarController.updateLabel(button)
  }

  def actionReload(event: ActionEvent): Unit = {
    actionClickButton(event)
    applicationController.reload()
  }

}

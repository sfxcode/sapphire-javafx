package com.sfxcode.sapphire.javafx.controller

import javafx.event.ActionEvent

abstract class SFXDetailController extends SFXEditorController {

  var masterTableController: Option[SFXMasterController] = None

  def actionSaveAndReturn(event: ActionEvent): Unit = {
    actionSave(event)
    actionReturn(event)
  }

  def actionRevertAndReturn(event: ActionEvent): Unit = {
    actionRevert(event)
    actionReturn(event)
  }

  def actionReturn(event: ActionEvent): Unit =
    masterTableController.foreach {
      masterController =>
        navigateToMasterController(masterController)
        masterController.table.getSelectionModel.select(masterController.lastSelected)
    }

  def navigateToMasterController(masterController: SFXMasterController): Unit

}

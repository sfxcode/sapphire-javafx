package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.javafx.controller.SFXViewController
import javafx.fxml.FXML
import javafx.scene.control._

abstract class SFXTabController extends SFXViewController {

  @FXML var tabPane: TabPane = _

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    tabPane.getSelectionModel.selectedItemProperty.addListener { (_, oldValue, newValue) =>
      tabPaneHasChanged(oldValue, newValue)
    }
  }

  def selectedTab: Tab = tabPane.getSelectionModel.getSelectedItem

  def tabPaneHasChanged(oldValue: Tab, newValue: Tab): Unit

}

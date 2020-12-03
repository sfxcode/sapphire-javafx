package com.sfxcode.sapphire.javafx.showcase.controller.form

import javafx.fxml.FXML

import com.sfxcode.sapphire.javafx.control.DualDataListView
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.{ PersonDatabase, Friend }
import com.typesafe.scalalogging.LazyLogging

class DualListFormController extends BaseController with LazyLogging {
  type R = Friend

  @FXML
  var dualDataList: DualDataListView[R] = _

  override def didGainVisibilityFirstTime(): Unit = {
    dualDataList.setItems(PersonDatabase.friends)
  }

  override def willGainVisibility(): Unit = {
    super.willGainVisibility()
  }

}

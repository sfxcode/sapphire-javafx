package com.sfxcode.sapphire.javafx.showcase.controller.table

import javafx.fxml.FXML
import javafx.scene.control.TableView
import com.sfxcode.sapphire.javafx.value.{ SFXBean, SFXBeanConversions }
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.{ Person, PersonDatabase }
import javafx.collections.ObservableList

class SimplePersonTableController extends BaseController with SFXBeanConversions {

  @FXML
  var table: TableView[SFXBean[Person]] = _

  def testString = "Test"

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    val items: ObservableList[SFXBean[Person]] = PersonDatabase.smallPersonList
    table.setItems(items)
  }
}

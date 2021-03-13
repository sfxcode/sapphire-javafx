package com.sfxcode.sapphire.javafx.showcase.controller.form

import com.sfxcode.sapphire.javafx.value.SFXBeanConversions
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import com.sfxcode.sapphire.javafx.control.SFXDataListView
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.{ Friend, Person, PersonDatabase }
import com.typesafe.scalalogging.LazyLogging
import javafx.collections.{ FXCollections, ObservableList }

import scala.jdk.CollectionConverters._
class ListFormController extends BaseController with SFXBeanConversions with LazyLogging {

  type R = Friend

  @FXML
  var comboBox: ComboBox[String] = _

  @FXML
  var listView: SFXDataListView[R] = _

  @FXML
  var dataList: SFXDataListView[R] = _

  val personsMap: Map[String, Person] = PersonDatabase.smallPersonList.map(value => (value.bean.name, value)).toMap
  val buffer: ObservableList[String] = FXCollections.observableArrayList[String]
  buffer.addAll(personsMap.keys.toList.asJava)

  override def didGainVisibilityFirstTime(): Unit = {
    comboBox.getSelectionModel.selectedItemProperty.addListener((_, _, newValue) => comboBoxDidChange(newValue))
    comboBox.setItems(buffer)

    listView.cellProperty.set("Name: ${_self.name()} (${_self.id()})")
    listView.showHeader.set(false)
    listView.showFooter.set(true)
    listView.footerTextProperty.set("found %s friends")

    comboBox.getSelectionModel.selectFirst()

    // update data list values
    dataList.cellProperty.set("Name: ${_self.name()} ID: ${_self.id()}")
    dataList.filterPromptProperty.set("Name")

    dataList.setItems(PersonDatabase.friends)
    dataList.listView.setOnMouseClicked(event => if (event.getClickCount == 2) deleteSelected())
  }

  def deleteSelected() {
    val selected = dataList.listView.getSelectionModel.getSelectedItems
    selected.foreach { v =>
      dataList.remove(v)
    }
  }

  override def willGainVisibility(): Unit = {
    super.willGainVisibility()
    comboBox.getSelectionModel.selectFirst()
  }

  def comboBoxDidChange(newValue: String) {
    logger.debug(personsMap(newValue).bean.friends.toString())
    listView.setItems(personsMap(newValue).bean.friends)

  }

  def actionResetList(event: ActionEvent) {
    dataList.setItems(PersonDatabase.friends)
  }

}

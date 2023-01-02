package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.data.reflect.FieldRegistry
import com.sfxcode.sapphire.javafx.value.SFXBean
import com.sfxcode.sapphire.javafx.filter.SFXDataTableFilter
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.layout.HBox
import com.sfxcode.sapphire.javafx.SFXCollectionExtensions._
import com.sfxcode.sapphire.javafx.SFXLogging

import java.lang.reflect.Field
import scala.reflect.ClassTag

abstract class SFXDataTableController extends SFXViewController with SFXLogging {

  type R <: AnyRef

  def ct: ClassTag[R]

  val fieldMap: Map[String, Field] = FieldRegistry.fieldMap(ct.runtimeClass)

  @FXML
  var table: TableView[SFXBean[R]] = _

  @FXML
  var searchBox: HBox = _

  var tableFilter: SFXDataTableFilter[R] = _

  def items: ObservableList[SFXBean[R]]

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()

    val itemsProperty = new SimpleObjectProperty[ObservableList[SFXBean[R]]](this, "", items)
    tableFilter = new SFXDataTableFilter[R](table, itemsProperty, new SimpleObjectProperty(this, "", searchBox))(ct)

    if (shouldAddColunns) {
      tableFilter.addColumns(shouldAddEditableColunns)
    }

    initTable(tableFilter)

    tableFilter.selectedItem.addListener(
      (_, oldValue, newValue) => selectedTableViewItemDidChange(oldValue, newValue))
    tableFilter.selectedItems.addChangeListener(
      _ => selectedTableViewItemsDidChange(tableFilter.selectedItems))
  }

  def shouldAddColunns: Boolean = true

  def shouldAddEditableColunns: Boolean = false

  def initTable(tableFilter: SFXDataTableFilter[R]): Unit = {}

  def selectedTableViewItemsDidChange(source: ObservableList[SFXBean[R]]): Unit =
    logger.debug("new values count: %s".format(source.size))

  def selectedTableViewItemDidChange(oldValue: SFXBean[R], newValue: SFXBean[R]): Unit =
    logger.debug("new value: %s".format({
      if (newValue != null) {
        newValue.bean
      } else {
        null
      }
    }))

}

package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.sfxcode.sapphire.javafx.value.SFXBean
import com.sfxcode.sapphire.javafx.filter.SFXDataTableFilter
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.layout.HBox
import com.sfxcode.sapphire.javafx.SFXCollectionExtensions._
import scala.reflect.runtime.{ universe => ru }

import scala.reflect.ClassTag

abstract class BaseDataTableController extends DataTableController

abstract class DataTableController extends SFXViewController with LazyLogging {
  val mirror: ru.Mirror = ru.runtimeMirror(this.getClass.getClassLoader)

  type R <: AnyRef

  def ct: ClassTag[R]

  // reflection
  val members = mirror.classSymbol(ct.runtimeClass).asType.typeSignature.members.toList.reverse
  logger.debug(
    members
      .collect({ case x if x.isTerm => x.asTerm })
      .filter(t => t.isVal || t.isVar)
      .map(m => m.name.toString)
      .toString())

  @FXML
  var table: TableView[SFXBean[R]] = _

  @FXML
  var searchBox: HBox = _

  var tableFilter: SFXDataTableFilter[R] = _

  def items: ObservableList[SFXBean[R]]

  override def didGainVisibilityFirstTime() {
    super.didGainVisibilityFirstTime()

    val itemsProperty = new SimpleObjectProperty[ObservableList[SFXBean[R]]](this, "", items)
    tableFilter = new SFXDataTableFilter[R](table, itemsProperty, new SimpleObjectProperty(this, "", searchBox))(ct)

    if (shouldAddColunns)
      tableFilter.addColumns()

    initTable(tableFilter)

    tableFilter.selectedItem.addListener((_, oldValue, newValue) => selectedTableViewItemDidChange(oldValue, newValue))
    tableFilter.selectedItems.addChangeListener(_ => selectedTableViewItemsDidChange(tableFilter.selectedItems))
  }

  def shouldAddColunns = true

  def initTable(tableFilter: SFXDataTableFilter[R]): Unit = {}

  def selectedTableViewItemsDidChange(source: ObservableList[SFXBean[R]]): Unit =
    logger.debug("new values count: %s".format(source.size))

  def selectedTableViewItemDidChange(oldValue: SFXBean[R], newValue: SFXBean[R]): Unit =
    logger.debug("new value: %s".format({
      if (newValue != null)
        newValue.bean
      else
        null
    }))

}

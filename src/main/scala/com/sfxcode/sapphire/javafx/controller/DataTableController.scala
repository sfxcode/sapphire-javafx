package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.javafx.controller.ViewController
import com.sfxcode.sapphire.javafx.value.FXBean
import com.sfxcode.sapphire.javafx.filter.DataTableFilter
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.layout.HBox
import com.sfxcode.sapphire.javafx.CollectionExtensions._
import scala.reflect.runtime.{universe => ru}

import scala.reflect.ClassTag

abstract class BaseDataTableController extends DataTableController

abstract class DataTableController extends ViewController with LazyLogging {
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
      .toString()
  )

  @FXML
  var table: TableView[FXBean[R]] = _

  @FXML
  var searchBox: HBox = _

  var tableFilter: DataTableFilter[R] = _

  def items: ObservableList[FXBean[R]]

  override def didGainVisibilityFirstTime() {
    super.didGainVisibilityFirstTime()

    val itemsProperty = new SimpleObjectProperty[ObservableList[FXBean[R]]](this, "", items)
    tableFilter = new DataTableFilter[R](table, itemsProperty, new SimpleObjectProperty(this, "", searchBox))(ct)

    if (shouldAddColunns)
      tableFilter.addColumns()

    initTable(tableFilter)

    tableFilter.selectedItem.addListener((_, oldValue, newValue) => selectedTableViewItemDidChange(oldValue, newValue))
    tableFilter.selectedItems.addChangeListener(_ => selectedTableViewItemsDidChange(tableFilter.selectedItems))
  }

  def shouldAddColunns = true

  def initTable(tableFilter: DataTableFilter[R]): Unit = {}

  def selectedTableViewItemsDidChange(source: ObservableList[FXBean[R]]): Unit =
    logger.debug("new values count: %s".format(source.size))

  def selectedTableViewItemDidChange(oldValue: FXBean[R], newValue: FXBean[R]): Unit =
    logger.debug("new value: %s".format({
      if (newValue != null)
        newValue.bean
      else
        null
    }))

}

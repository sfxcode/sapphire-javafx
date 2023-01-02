package com.sfxcode.sapphire.javafx.filter

import com.sfxcode.sapphire.data.reflect.FieldRegistry
import javafx.scene.layout.Pane
import com.sfxcode.sapphire.javafx.control.{ SFXTableCellFactory, SFXTableValueFactory }
import com.sfxcode.sapphire.javafx.value.SFXBean
import com.sfxcode.sapphire.javafx.control.table.SFXTableColumnFactory
import javafx.beans.property.ReadOnlyObjectProperty

import scala.collection.mutable
import scala.reflect.ClassTag
import javafx.beans.property.ObjectProperty
import javafx.collections.ObservableList
import javafx.scene.control.{ TableView, _ }
import javafx.scene.text.TextAlignment
import com.sfxcode.sapphire.javafx.SFXCollectionExtensions._

import java.lang.reflect.Field

class SFXDataTableFilter[S <: AnyRef](table: TableView[SFXBean[S]], items: ObjectProperty[ObservableList[SFXBean[S]]], pane: ObjectProperty[Pane])(implicit ct: ClassTag[S]) extends SFXDataFilter[S](items, pane) {

  // columns
  val columnMapping = new mutable.HashMap[String, TableColumn[SFXBean[S], _]]()

  val columnPropertyMap = new mutable.HashMap[String, String]()
  val columnHeaderMap = new mutable.HashMap[String, String]()

  val fieldMap: Map[String, Field] = FieldRegistry.fieldMap(ct.runtimeClass)

  filterResult.addChangeListener {
    _ =>
      table.getItems.clear()
      table.getItems.addAll(filterResult)
      table.sort()
  }

  filter()

  override def itemsHasChanged(): Unit = {
    super.itemsHasChanged()
    table.autosize()
    table.layout()
  }

  def addColumn(key: String, column: TableColumn[SFXBean[S], _]): Unit = {
    table.getColumns.add(column)
    columnMapping.put(key, column)
  }

  def addColumns[T](
    editable: Boolean = false,
    numberFormat: String = SFXTableColumnFactory.DefaultNumberFormat,
    decimalFormat: String = SFXTableColumnFactory.DefaultDecimalFormat): Unit = {
    val columnList =
      SFXTableColumnFactory.columnListFromMembers[S, T](fieldMap, columnHeaderMap.toMap, columnPropertyMap.toMap, editable, numberFormat, decimalFormat)

    columnList._1.foreach(
      key => addColumn(key, columnList._2(key)))
  }

  def addColumn[T](header: String, property: String, alignment: TextAlignment = TextAlignment.LEFT): TableColumn[SFXBean[S], T] = {
    val valueFactory = new SFXTableValueFactory[SFXBean[S], T]()
    valueFactory.property = columnPropertyMap.getOrElse(property, property)
    val cellFactory = new SFXTableCellFactory[SFXBean[S], T]()
    cellFactory.alignment = alignment

    val result = SFXTableColumnFactory.columnFromFactories[S, T](header, valueFactory, Some(cellFactory))
    addColumn(header, result)
    result
  }

  def getColumn[T](property: String): Option[TableColumn[SFXBean[S], _]] =
    columnMapping.get(property)

  def getTable: TableView[SFXBean[S]] = table

  def getItems: ObservableList[SFXBean[S]] = table.getItems

  def hideColumn(name: String*): Unit = name.foreach(
    name =>
      getColumn(name).foreach(
        c => c.setVisible(false)))

  def showColumn(name: String*): Unit = name.foreach(
    name =>
      getColumn(name).foreach(
        c => c.setVisible(true)))

  def setColumnText(name: String, text: String): Unit = getColumn(name).foreach(
    c => c.setText(text))

  def setColumnPrefWidth(name: String, value: Double): Unit = getColumn(name).foreach(
    c => c.setPrefWidth(value))

  def selectedBean: SFXBean[S] = table.getSelectionModel.selectedItemProperty.get

  def selectedItem: ReadOnlyObjectProperty[SFXBean[S]] = table.getSelectionModel.selectedItemProperty

  def selectedItems: ObservableList[SFXBean[S]] = table.getSelectionModel.getSelectedItems

}

package com.sfxcode.sapphire.javafx.filter

import javafx.scene.layout.Pane
import com.sfxcode.sapphire.javafx.control.{SFXTableCellFactory, SFXTableValueFactory}
import com.sfxcode.sapphire.javafx.value.SFXBean
import com.sfxcode.sapphire.javafx.control.table.SFXTableColumnFactory
import javafx.beans.property.ReadOnlyObjectProperty

import scala.collection.mutable
import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}
import javafx.beans.property.ObjectProperty
import javafx.collections.ObservableList
import javafx.scene.control.{TableView, _}
import javafx.scene.text.TextAlignment
import com.sfxcode.sapphire.javafx.SFXCollectionExtensions._

class SFXDataTableFilter[S <: AnyRef](
    table: TableView[SFXBean[S]],
    items: ObjectProperty[ObservableList[SFXBean[S]]],
    pane: ObjectProperty[Pane]
)(implicit ct: ClassTag[S])
    extends SFXDataFilter[S](items, pane) {

  // columns
  val columnMapping = new mutable.HashMap[String, TableColumn[SFXBean[S], _]]()

  val columnPropertyMap = new mutable.HashMap[String, String]()
  val columnHeaderMap   = new mutable.HashMap[String, String]()

  // reflection
  private val mirror  = ru.runtimeMirror(ct.runtimeClass.getClassLoader)
  private val members = mirror.classSymbol(ct.runtimeClass).asType.typeSignature.members.toList.reverse
  logger.debug(
    members
      .collect({ case x if x.isTerm => x.asTerm })
      .filter(t => t.isVal || t.isVar)
      .map(m => m.name.toString)
      .toString()
  )

  filterResult.addChangeListener { _ =>
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
      numberFormat: String = "#,##0",
      decimalFormat: String = "#,##0.00"
  ): Unit = {
    val columnList = SFXTableColumnFactory.columnListFromMembers[S, T](
      members,
      columnHeaderMap.toMap,
      columnPropertyMap.toMap,
      editable,
      numberFormat,
      decimalFormat
    )

    columnList._1.foreach(key => addColumn(key, columnList._2(key)))
  }

  def addColumn[T](
      header: String,
      property: String,
      alignment: TextAlignment = TextAlignment.LEFT
  ): TableColumn[SFXBean[S], T] = {
    val valueFactory = new SFXTableValueFactory[SFXBean[S], T]()
    valueFactory.setProperty(columnPropertyMap.getOrElse(property, property))
    val cellFactory = new SFXTableCellFactory[SFXBean[S], T]()
    cellFactory.setAlignment(alignment)

    val result = SFXTableColumnFactory.columnFromFactories[S, T](header, valueFactory, Some(cellFactory))
    addColumn(header, result)
    result
  }

  def getColumn[T](property: String): Option[TableColumn[SFXBean[S], _]] =
    columnMapping.get(property)

  def getTable: TableView[SFXBean[S]] = table

  def getItems: ObservableList[SFXBean[S]] = table.getItems

  def hideColumn(name: String*): Unit = name.foreach(name => getColumn(name).foreach(c => c.setVisible(false)))

  def showColumn(name: String*): Unit = name.foreach(name => getColumn(name).foreach(c => c.setVisible(true)))

  def setColumnText(name: String, text: String): Unit = getColumn(name).foreach(c => c.setText(text))

  def setColumnPrefWidth(name: String, value: Double): Unit = getColumn(name).foreach(c => c.setPrefWidth(value))

  def selectedBean: SFXBean[S] = table.getSelectionModel.selectedItemProperty.get

  def selectedItem: ReadOnlyObjectProperty[SFXBean[S]] = table.getSelectionModel.selectedItemProperty

  def selectedItems: ObservableList[SFXBean[S]] = table.getSelectionModel.getSelectedItems

}

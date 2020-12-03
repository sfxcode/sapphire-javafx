package com.sfxcode.sapphire.javafx.control.table

import javafx.scene.control.cell.{CheckBoxTableCell, TextFieldTableCell}
import javafx.scene.control.{TableCell, TableColumn}
import javafx.util.{Callback}

import scala.beans.BeanProperty

abstract class FXCellFactory[S, T] extends Callback[TableColumn[S, T], TableCell[S, T]] {

  @BeanProperty
  var alignment: Any = "left"

  @BeanProperty
  var converter: String = _

  def updateCell(column: TableColumn[S, T], cell: TableCell[S, T]): TableCell[S, T] =
    cell
}

class FXCheckBoxCellFactory[S, T] extends FXCellFactory[S, T] {

  def call(column: TableColumn[S, T]): TableCell[S, T] =
    updateCell(column, new CheckBoxTableCell[S, T]())
}

class FXTextFieldCellFactory[S, T] extends FXCellFactory[S, T] {

  def call(column: TableColumn[S, T]): TableCell[S, T] =
    updateCell(column, new TextFieldTableCell[S, T]())
}

package com.sfxcode.sapphire.javafx.control

import com.sfxcode.sapphire.javafx.application.SFXApplicationEnvironment
import javafx.geometry.Pos
import javafx.scene.control.IndexedCell
import javafx.scene.control.cell.{ TextFieldTableCell, TextFieldTreeTableCell }
import javafx.scene.text.TextAlignment
import javafx.util.StringConverter

import scala.beans.BeanProperty

trait SFXCellFactory[S, T] {

  def defaultClassName: String

  @BeanProperty
  var packageName: String = "javafx.scene.control.cell."

  @BeanProperty
  var simpleClassName: String = defaultClassName

  @BeanProperty
  var alignment: Any = "left"

  @BeanProperty
  var converter: String = _

  protected def updateCell(cell: IndexedCell[T]): Unit = {
    if (alignment == TextAlignment.CENTER || alignment.toString.equalsIgnoreCase("center"))
      cell.setAlignment(Pos.CENTER)
    else if (alignment == TextAlignment.RIGHT || alignment.toString.equalsIgnoreCase("right"))
      cell.setAlignment(Pos.CENTER_RIGHT)
    else
      cell.setAlignment(Pos.CENTER_LEFT)

    if (converter != null)
      cell match {
        case textFieldCell: TextFieldTableCell[S, T] => textFieldCell.setConverter(getConverterForName(converter))
        case textFieldCell: TextFieldTreeTableCell[S, T] => textFieldCell.setConverter(getConverterForName(converter))
      }

    def getConverterForName(name: String): StringConverter[T] =
      SFXApplicationEnvironment.getConverterByName(name)

  }

}

package com.sfxcode.sapphire.javafx.control

import com.sfxcode.sapphire.javafx.value.SFXBean
import javafx.scene.control._
import javafx.util.Callback

import scala.beans.BeanProperty

class SFXListCellFactory[S <: AnyRef] extends Callback[ListView[SFXBean[S]], ListCell[SFXBean[S]]] {
  @BeanProperty
  var property = ""

  def call(column: ListView[SFXBean[S]]): ListCell[SFXBean[S]] =
    new FXListCell[SFXBean[S]](property)

}

object FXListCellFactory {

  def apply[S <: AnyRef](property: String): SFXListCellFactory[S] = {
    val result = new SFXListCellFactory[S]()
    result.setProperty(property)
    result
  }
}

class FXListCell[R](property: String = "") extends ListCell[R] {

  override def updateItem(item: R, empty: Boolean): Unit = {
    super.updateItem(item, empty)
    item match {
      case b: SFXBean[_] =>
        val value = b.getValue(property)
        value match {
          case v: Any => textProperty().set(v.toString)
          case _      => textProperty().set("NULL VALUE")
        }
      case b: Any => textProperty().set(b.toString)
      case _      => textProperty().set("")
    }
  }
}

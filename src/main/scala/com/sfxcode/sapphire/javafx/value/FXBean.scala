package com.sfxcode.sapphire.javafx.value

import com.sfxcode.sapphire.javafx.ConfigValues
import com.sfxcode.sapphire.data.reflect.FieldMeta._
import com.sfxcode.sapphire.data.{ DataAdapter }
import com.sfxcode.sapphire.data.reflect.FieldMeta
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property.Property

class FXBean[T <: AnyRef](val bean: T, typeHints: List[FieldMeta] = EmptyTypeHints)
  extends DataAdapter[T](bean, typeHints)
  with LazyLogging {

  def getBean: AnyRef = wrappedData

  def beanValueChanged(key: String, property: Property[_], oldValue: Any, newValue: Any) {
    updateObservableValue(property, newValue)
  }

}

object FXBean extends ConfigValues {

  def apply[T <: AnyRef](bean: T, typeHints: List[FieldMeta] = List[FieldMeta]()): FXBean[T] =
    new FXBean[T](bean, typeHints)
}

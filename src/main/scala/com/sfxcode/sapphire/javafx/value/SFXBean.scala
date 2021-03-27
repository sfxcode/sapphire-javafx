package com.sfxcode.sapphire.javafx.value

import com.sfxcode.sapphire.data.reflect.FieldMeta._
import com.sfxcode.sapphire.data.{Configuration, DataAdapter}
import com.sfxcode.sapphire.data.reflect.FieldMeta
import com.typesafe.scalalogging.LazyLogging

class SFXBean[T <: AnyRef](val bean: T, typeHints: List[FieldMeta] = EmptyTypeHints)
    extends DataAdapter[T](bean, typeHints)
    with LazyLogging {

  def getBean: AnyRef = wrappedData

}

object SFXBean extends Configuration {

  def apply[T <: AnyRef](bean: T, typeHints: List[FieldMeta] = List[FieldMeta]()): SFXBean[T] =
    new SFXBean[T](bean, typeHints)
}

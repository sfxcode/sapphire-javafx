package com.sfxcode.sapphire.javafx.value

import com.sfxcode.sapphire.data.DataAdapter
import com.sfxcode.sapphire.data.el.ObjectExpressionHelper
import com.sfxcode.sapphire.data.reflect.FieldMeta._
import com.sfxcode.sapphire.data.reflect.FieldMeta
import com.sfxcode.sapphire.javafx.Configuration
import com.sfxcode.sapphire.javafx.value.SFXBean.FxmlExpressionPrefix

import scala.jdk.CollectionConverters.MapHasAsScala

class SFXBean[T <: AnyRef](val bean: T, typeHints: List[FieldMeta] = EmptyTypeHints)
  extends SFXBeanProperties[T](bean, typeHints) {

  override def createChildForKey(key: String, value: Any): DataAdapter[AnyRef] = {
    if (!childrenMap.contains(key)) {
      val adapter = SFXBean(value.asInstanceOf[AnyRef])
      adapter.parentDataAdapter = Some(this.asInstanceOf[SFXBean[AnyRef]])
      adapter.trackChanges = trackChanges
      childrenMap.+=(key -> adapter)
    }
    childrenMap(key)
  }

  override protected def shouldHandleRelations(key: String): Boolean =
    key.contains(".") && !key.contains(ObjectExpressionHelper.ExpressionPrefix) &&
      !key.contains(FxmlExpressionPrefix)

  override def updateValue(key: String, valueToUpdate: Any): Unit = {
    super.updateValue(key, valueToUpdate)
    val property = propertyMap.asScala.getOrElse(key, getProperty(key))
    updateObservableValue(property, valueToUpdate)

  }

}

object SFXBean extends Configuration {
  val FxmlExpressionPrefix: String = "!{"

  def apply[T <: AnyRef](bean: T, typeHints: List[FieldMeta] = List[FieldMeta]()): SFXBean[T] =
    new SFXBean[T](bean, typeHints)
}

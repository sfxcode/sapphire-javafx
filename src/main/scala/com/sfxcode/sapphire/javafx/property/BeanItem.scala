package com.sfxcode.sapphire.javafx.property

import java.time.{Instant, LocalDate, LocalDateTime, ZoneId}
import java.util.{Date, Optional}

import com.sfxcode.sapphire.data.reflect.{FieldMetaRegistry, FieldRegistry}
import com.sfxcode.sapphire.javafx.ConfigValues
import com.sfxcode.sapphire.javafx.value._
import javafx.beans.value.ObservableValue
import javafx.collections.{FXCollections, ObservableList}
import org.controlsfx.control.PropertySheet.Item

import scala.reflect.ClassTag

final class EmptyBeanItemClass

private object EmptyBeanItemClass {
  val ClazzOf: Class[_ <: EmptyBeanItemClass] = EmptyBeanItemClass().getClass

  def apply(): EmptyBeanItemClass = new EmptyBeanItemClass
}

class BeanItem(
    var bean: FXBean[_ <: AnyRef],
    key: String,
    name: String = "",
    category: String = "",
    description: String = "",
    editable: Boolean = true,
    clazz: Class[_] = EmptyBeanItemClass.ClazzOf
) extends Item
    with ConfigValues {
  var classOption: Option[Class[_]] = Some(clazz)

  def getKey: String = key

  override def getType: Class[_] = {
    var clazz: Class[_] = EmptyBeanItemClass.ClazzOf
    if (classOption.isDefined && EmptyBeanItemClass.ClazzOf != classOption.get)
      clazz = classOption.get
    else {
      val underlying = bean.bean
      if (underlying.isInstanceOf[Map[String, Any]] || underlying.isInstanceOf[java.util.Map[String, Any]]) {
        clazz = classOf[String]
        val valueOption = Option(bean.getValue(key))
        if (valueOption.isDefined)
          clazz = valueOption.get.getClass
        classOption = Some(clazz)
      }
      else {
        val memberInfo = FieldMetaRegistry.fieldMeta(underlying, key)
        clazz = memberInfo.field.getType
        classOption = Some(clazz)
      }
    }
    if (clazz == classOf[Date])
      classOf[LocalDate]
    else
      clazz
  }

  def isDateType: Boolean = classOption.isDefined && classOption.get == classOf[Date]

  override def getValue: AnyRef = {
    val valueOption = Option(bean.getValue(key))
    if (valueOption.isDefined && isDateType)
      asLocalDate(valueOption.get.asInstanceOf[Date])
    else if (valueOption.isDefined)
      valueOption.get.asInstanceOf[AnyRef]
    else
      null
  }

  override def setValue(value: Any): Unit = {
    val valueOption = Option(value)
    if (valueOption.isDefined && isDateType && valueOption.get.isInstanceOf[LocalDate])
      bean.updateValue(key, asDate(valueOption.get.asInstanceOf[LocalDate]))
    else if (valueOption.isDefined)
      bean.updateValue(key, valueOption.get)
    else
      bean.updateValue(key, valueOption.orNull)
  }

  override def getCategory: String =
    if (category.isEmpty)
      configStringValue("sapphire.javafx.properties.beanItem.defaultCategory")
    else
      category

  override def getName: String =
    if (name.isEmpty)
      key
    else
      name

  override def getDescription: String = description

  override def isEditable: Boolean = editable

  def asLocalDate(date: java.util.Date): LocalDate = {
    val instant = Instant.ofEpochMilli(date.getTime)
    LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate
  }

  def asDate(date: LocalDate): java.util.Date = {
    val instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant
    Date.from(instant)
  }

  override def getObservableValue: Optional[ObservableValue[_]] =
    Optional.of(bean.getProperty(key).asInstanceOf[ObservableValue[_]])
}

object BeanItem {

  def apply(
      bean: FXBean[_ <: AnyRef],
      key: String,
      name: String = "",
      category: String = "",
      description: String = "",
      editable: Boolean = true,
      clazz: Class[_] = EmptyBeanItemClass.ClazzOf
  ): BeanItem =
    new BeanItem(bean, key, name, category, description, editable, clazz)

  def beanItems[T <: AnyRef](bean: FXBean[T])(implicit ct: ClassTag[T]): ObservableList[Item] = {
    val result   = FXCollections.observableArrayList[Item]()
    val fieldMap = FieldRegistry.fieldMap(ct.runtimeClass)
    fieldMap.keys.foreach { key =>
      result.add(BeanItem(bean, key))
    }
    result
  }

}

package com.sfxcode.sapphire.javafx.property

import com.sfxcode.sapphire.data.reflect.FieldRegistry
import com.sfxcode.sapphire.javafx.SFXCollectionExtensions._
import com.sfxcode.sapphire.javafx.application.DefaultsTo
import com.sfxcode.sapphire.javafx.value._
import javafx.collections.{FXCollections, ObservableList}

import scala.jdk.CollectionConverters._
import scala.reflect.ClassTag

class SFXBeanItems[T <: AnyRef]() {
  private val itemBuffer               = FXCollections.observableArrayList[BeanItem]()
  private var beanItemBean: SFXBean[T] = _

  def getItems: ObservableList[BeanItem] = itemBuffer

  def addItem(
      key: String,
      name: String = "",
      category: String = "",
      description: String = "",
      editable: Boolean = true,
      clazz: Class[_] = EmptyBeanItemClass.ClazzOf
  ): BeanItem = {
    val beanItem = BeanItem(beanItemBean, key, name, category, description, editable, clazz)
    itemBuffer.add(beanItem)
    beanItem
  }

  def addItems[T <: AnyRef](implicit ct: ClassTag[T]): Unit = {
    val fieldMap = FieldRegistry.fieldMap(ct.runtimeClass)
    fieldMap.keys.foreach { key =>
      addItem(key)
    }
  }

  def addItemsFromMap(scalaMap: Map[String, Any]): Unit =
    scalaMap.keys.foreach { key =>
      val value = scalaMap.get(key)
      value.foreach(value => addItem(key, clazz = value.getClass))
    }

  def updateBeanValue(newValue: T): Unit = {
    beanItemBean = SFXBean[T](newValue)
    itemBuffer.foreach(item => item.bean = beanItemBean)
  }

  def updateBean(newBean: SFXBean[T]): Unit = {
    beanItemBean = newBean
    itemBuffer.foreach(item => item.bean = beanItemBean)
  }

  def beanItem(key: String): Option[BeanItem] =
    itemBuffer.asScala.find(item => key.equals(item.getKey))

  def bean: SFXBean[T] = beanItemBean

  def beanValue: T = beanItemBean.bean

}

object SFXBeanItems {

  def apply[T <: AnyRef]()(implicit e: T DefaultsTo AnyRef): SFXBeanItems[T] = new SFXBeanItems[T]()

  def apply[T <: AnyRef](bean: SFXBean[T])(implicit e: T DefaultsTo AnyRef): SFXBeanItems[T] = {
    val result = new SFXBeanItems[T]()
    result.updateBean(bean)
    result
  }

}

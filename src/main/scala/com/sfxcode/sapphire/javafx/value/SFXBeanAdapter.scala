package com.sfxcode.sapphire.javafx.value

import com.sfxcode.sapphire.javafx.application.SFXApplicationEnvironment
import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property._
import javafx.beans.{ property => jfxbp }
import javafx.collections.{ FXCollections, ObservableMap }
import javafx.scene.Node
import javafx.util.StringConverter

import scala.jdk.CollectionConverters._

class SFXBeanAdapter[T <: AnyRef](val viewController: SFXViewController, var parent: Node = null) extends SFXKeyConverter with LazyLogging {

  val beanProperty = new SimpleObjectProperty[SFXBean[T]]()

  val hasBeanProperty = new SimpleBooleanProperty(false)

  hasBeanProperty.bind(beanProperty.isNotNull)

  beanProperty.addListener(
    (_, oldValue, newValue) => updateBean(oldValue, newValue))

  val nodeCache: ObservableMap[String, Option[Node]] =
    FXCollections.observableHashMap[String, Option[javafx.scene.Node]]()

  val bindingMap: ObservableMap[Property[_], String] = FXCollections.observableHashMap[Property[_ <: Any], String]()

  val boundProperties: ObservableMap[Property[_], Property[_]] =
    FXCollections.observableHashMap[Property[_], Property[_]]()

  if (parent == null)
    parent = viewController.rootPane

  def get: SFXBean[T] = beanProperty.get

  def unset(): Unit = set(null)

  def set(newValue: SFXBean[T]): Unit = beanProperty.setValue(newValue)

  def addBinding(property: Property[_], beanKey: String, converter: Option[StringConverter[T]] = None): Unit = {
    if (converter.isDefined && property.isInstanceOf[StringProperty])
      converterMap.put(property.asInstanceOf[StringProperty], converter.get)
    bindingMap.put(property.asInstanceOf[Property[Any]], beanKey)
  }

  def addBindings(keyBindings: SFXKeyBindings): Unit =
    keyBindings.keys.foreach {
      key =>
        val property = guessPropertyForNode(key)
        if (property.isDefined)
          bindingMap.put(property.get, keyBindings(key))
    }

  def guessPropertyForNode(key: String): Option[Property[_]] = {
    val node = nodeCache.asScala.getOrElse(key, viewController.locate(key, parent))
    if (node.isDefined) {
      if (!nodeCache.containsKey(key))
        nodeCache.put(key, node)
      val result = SFXApplicationEnvironment.nodePropertyResolver.resolve(node.get)
      logger.debug("resolved property for %s : %s".format(key, result))
      result
    } else {
      logger.warn("can not resolve property for key %s - try to create FXBeanAdapter with parent node".format(key))
      None
    }

  }

  def revert(): Unit =
    if (hasValue)
      beanProperty.getValue.revert()

  def clearChanges(): Unit =
    if (hasValue)
      beanProperty.getValue.clearChanges()

  def hasValue: Boolean = hasBeanProperty.get

  protected def updateBean(oldValue: SFXBean[T], newValue: SFXBean[T]): Unit = {
    unbindAll()
    bindAll(newValue)
  }

  protected def unbindAll(): Unit = {
    boundProperties.keySet.asScala.foreach {
      p =>
        val p1 = p.asInstanceOf[jfxbp.Property[Any]]
        val p2 = boundProperties.get(p).asInstanceOf[jfxbp.Property[Any]]
        p1.unbindBidirectional(p2)
    }
    boundProperties.clear()
  }

  protected def bindAll(bean: SFXBean[T]): Unit =
    if (hasValue)
      bindingMap
        .keySet()
        .asScala
        .foreach(
          property => bindBidirectional(bean, property, bindingMap.get(property)))

  protected def bindBidirectional[S](bean: SFXBean[T], property: Property[S], beanKey: String): Unit = {
    val observable = bean.getProperty(beanKey)
    observable match {
      case beanProperty: Property[_] =>
        property match {
          case stringProperty: StringProperty =>
            bindBidirectionalFromStringProperty(stringProperty, observable, beanKey)
          case p: Property[S] => bindBidirectionalFromProperty[S](p, beanProperty.asInstanceOf[Property[S]])
        }
      case null =>
    }
  }

  protected def bindBidirectionalFromStringProperty[S](stringProperty: StringProperty, beanProperty: Property[S], beanKey: String): Unit = {
    val converter = converterMap.asScala.get(stringProperty)
    if (converter.isDefined) {
      val c = converter.get
      val bp = beanProperty.asInstanceOf[Property[S]]
      stringProperty.bindBidirectional[S](bp, c.asInstanceOf[StringConverter[S]])
      boundProperties.put(stringProperty, beanProperty)
    } else
      beanProperty match {
        case _: StringProperty =>
          val defaultStringConverter = SFXApplicationEnvironment.getConverterByName[Any]("DefaultStringConverter")
          stringProperty.bindBidirectional(beanProperty.asInstanceOf[Property[Any]], defaultStringConverter)
          boundProperties.put(stringProperty, beanProperty)
      }
  }

  protected def bindBidirectionalFromProperty[S](nodeProperty: Property[S], beanProperty: Property[S]): Unit = {
    nodeProperty.bindBidirectional(beanProperty)
    boundProperties.put(nodeProperty, beanProperty)
  }

}

object SFXBeanAdapter {

  def apply[T <: AnyRef](viewController: SFXViewController, parent: Node = null): SFXBeanAdapter[T] =
    new SFXBeanAdapter[T](viewController, parent)

}

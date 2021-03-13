package com.sfxcode.sapphire.javafx.value

import com.sfxcode.sapphire.data.reflect.{FieldRegistry, ReflectionTools}
import javafx.collections.{FXCollections, ObservableMap}

import scala.jdk.CollectionConverters._
import scala.collection.mutable
import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

class SFXKeyBindings {
  val bindingMap: ObservableMap[String, String] = FXCollections.observableHashMap[String, String]()

  def apply(key: String): String = bindingMap.get(key)

  def add(nodeKey: String, memberKey: String): SFXKeyBindings = {
    bindingMap.put(nodeKey, memberKey)
    this
  }

  def add(list: List[String], nodePrefix: String = ""): SFXKeyBindings = {
    list.foreach(key => bindingMap.put(nodePrefix + key, key))
    this
  }

  def add(map: Map[String, String]): SFXKeyBindings = {
    map.keys.foreach(key => bindingMap.put(key, map(key)))
    this
  }

  def keys: mutable.Set[String] = bindingMap.keySet().asScala

  override def toString: String = super.toString + " Bindings: " + bindingMap
}

object SFXKeyBindings {

  def apply(): SFXKeyBindings = new SFXKeyBindings

  def apply(args: String*): SFXKeyBindings = {
    val bindings = new SFXKeyBindings
    bindings.add(args.toList)
  }

  def apply(map: Map[String, String]): SFXKeyBindings = {
    val bindings = new SFXKeyBindings
    bindings.add(map)
  }

  def apply(list: List[String], nodePrefix: String = ""): SFXKeyBindings = {
    val bindings = new SFXKeyBindings
    bindings.add(list, nodePrefix)
  }

  def forClass[T <: AnyRef](nodePrefix: String = "")(implicit ct: ClassTag[T]): SFXKeyBindings = {
    val bindings = new SFXKeyBindings

    val fields = FieldRegistry.fieldMap(ct.runtimeClass)
    fields.keys.foreach { name =>
      bindings.add(nodePrefix + name, name)
    }
    bindings

  }

}

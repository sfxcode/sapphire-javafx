package com.sfxcode.sapphire.javafx.value

import java.util

import javafx.collections._

object SFXBeanCollections {

  def observableMap[K, V <: AnyRef]: ObservableMap[K, SFXBean[V]] =
    FXCollections.observableHashMap[K, SFXBean[V]]()

  def observableList[T <: AnyRef]: ObservableList[SFXBean[T]] =
    FXCollections.observableArrayList[SFXBean[T]]()

  def observableHashSet[T <: AnyRef]: ObservableSet[SFXBean[T]] =
    FXCollections.observableSet(new util.HashSet[SFXBean[T]]())

}

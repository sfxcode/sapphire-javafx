package com.sfxcode.sapphire.javafx.control

import javafx.beans.value.ObservableValue
import javafx.scene.control.TreeTableColumn
import javafx.util.Callback

class SFXTreeTableValueFactory[S <: AnyRef, T] extends Callback[TreeTableColumn.CellDataFeatures[S, T], ObservableValue[T]] with SFXValueFactory[S, T] {

  def call(features: TreeTableColumn.CellDataFeatures[S, T]): ObservableValue[T] = {
    val value: S = features.getValue.getValue
    resolveValue(value)
  }

}

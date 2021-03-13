package com.sfxcode.sapphire.javafx.control

import javafx.beans.value.ObservableValue
import javafx.scene.control.TableColumn
import javafx.scene.control.TableColumn.CellDataFeatures
import javafx.util.Callback

class SFXTableValueFactory[S <: AnyRef, T]
  extends Callback[TableColumn.CellDataFeatures[S, T], ObservableValue[T]]
  with SFXFXValueFactory[S, T] {

  def call(features: CellDataFeatures[S, T]): ObservableValue[T] = {
    val value: S = features.getValue
    resolveValue(value)
  }

}

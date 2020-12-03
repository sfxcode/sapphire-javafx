package com.sfxcode.sapphire.javafx.control

import javafx.scene.control.{ Control, Skin }

import com.sfxcode.sapphire.javafx.skin.DualDataListViewSkin

class DualDataListView[S <: AnyRef] extends Control {

  lazy val css = getClass.getResource("dualdatalistview.css").toExternalForm

  getStyleClass.add("dual-data-list-view")

  val leftDataListView = new DataListView[S]()

  val rightDataListView = new DataListView[S]()

  protected override def createDefaultSkin: Skin[DualDataListView[S]] =
    new DualDataListViewSkin[S](this)

  override def getUserAgentStylesheet: String = css

  def setItems(left: Iterable[S], right: Iterable[S] = List[S]()): Unit = {
    leftDataListView.setItems(left)
    rightDataListView.setItems(right)
  }

  def setCellProperty(value: String): Unit = {
    leftDataListView.cellProperty.set(value)
    rightDataListView.cellProperty.set(value)
  }

}

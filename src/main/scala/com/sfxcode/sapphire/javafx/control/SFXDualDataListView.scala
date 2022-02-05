package com.sfxcode.sapphire.javafx.control

import com.sfxcode.sapphire.javafx.assets.SFXResourceLoader
import javafx.scene.control.{ Control, Skin }
import com.sfxcode.sapphire.javafx.skin.SFXDualDataListViewSkin

class SFXDualDataListView[S <: AnyRef] extends Control {

  lazy val css: String = SFXResourceLoader.loadAsExternalForm("css/controls/sfx-dual-data-list.css")

  getStyleClass.add("sfx-dual-data-list")

  val leftDataListView = new SFXDataListView[S]()

  val rightDataListView = new SFXDataListView[S]()

  protected override def createDefaultSkin: Skin[SFXDualDataListView[S]] =
    new SFXDualDataListViewSkin[S](this)

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

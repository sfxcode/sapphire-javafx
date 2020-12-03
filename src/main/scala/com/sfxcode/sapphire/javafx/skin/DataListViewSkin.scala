package com.sfxcode.sapphire.javafx.skin

import javafx.scene.control.SkinBase
import com.sfxcode.sapphire.javafx.control.FXListCellFactory
import com.sfxcode.sapphire.javafx.control.DataListView
import javafx.scene.control._
import javafx.scene.layout.{ HBox, VBox }
class DataListViewSkin[S <: AnyRef](view: DataListView[S]) extends SkinBase[DataListView[S]](view) {

  val contentBox = new VBox()
  contentBox.setSpacing(5)
  contentBox.getStyleClass.add("content-box")

  val label = new Label("Footer Label")
  label.getStyleClass.add("footer-label")
  view.footerLabel.set(label)

  val headerBox = new HBox
  headerBox.getStyleClass.add("header-box")
  view.header.set(headerBox)

  val footerBox = new HBox
  footerBox.getStyleClass.add("footer-box")
  footerBox.getChildren.add(label)
  view.footer.set(footerBox)

  updateCellFactory()
  view.cellProperty.addListener((_, _, _) => updateView())

  view.header.addListener((_, _, _) => updateView())

  view.showHeader.addListener((_, _, _) => updateView())

  view.footer.addListener((_, _, _) => updateView())
  view.showFooter.addListener((_, _, _) => updateView())

  getChildren.add(contentBox)

  updateView()

  def updateView(): Unit = {
    contentBox.getChildren.clear()
    if (view.showHeader.get && view.header.get != null)
      contentBox.getChildren.add(view.header.get)

    contentBox.getChildren.add(view.listView)

    if (view.showFooter.get && view.footer.get != null)
      contentBox.getChildren.add(view.footer.get)
  }

  def updateCellFactory(): Unit = {
    val cellFactory = new FXListCellFactory[S]
    cellFactory.setProperty(view.cellProperty.get)
    view.listView.setCellFactory(cellFactory)
  }

}

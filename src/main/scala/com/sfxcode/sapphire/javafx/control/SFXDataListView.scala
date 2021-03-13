package com.sfxcode.sapphire.javafx.control

import javafx.scene.layout.Pane
import com.sfxcode.sapphire.javafx.value.{SFXBean, SFXBeanConversions}
import com.sfxcode.sapphire.javafx.filter.SFXDataListFilter
import com.sfxcode.sapphire.javafx.skin.SFXDataListViewSkin
import javafx.beans.property._
import javafx.collections.{FXCollections, ObservableList}
import javafx.scene.control.{Control, Label, ListView, Skin}
import com.sfxcode.sapphire.javafx.SFXCollectionExtensions._
import com.sfxcode.sapphire.javafx.assets.SFXResourceLoader

import scala.jdk.CollectionConverters._

class SFXDataListView[S <: AnyRef] extends Control with SFXBeanConversions {

  lazy val css: String = SFXResourceLoader.loadAsExternalForm("css/controls/sfx-data-list.css")

  getStyleClass.add("sfx-data-list")

  val items = new SimpleObjectProperty[ObservableList[SFXBean[S]]](
    this,
    "listViewItems",
    FXCollections.observableArrayList[SFXBean[S]]()
  )

  val listView = new ListView[SFXBean[S]]()

  val showHeader = new SimpleBooleanProperty(true)
  val header     = new SimpleObjectProperty[Pane](this, "listViewHeader")

  val showFooter = new SimpleBooleanProperty(false)
  val footer     = new SimpleObjectProperty[Pane](this, "listViewFooter")

  val footerLabel        = new SimpleObjectProperty[Label](this, "listViewFooterLabel")
  val footerTextProperty = new SimpleStringProperty("%s of %s items")

  val cellProperty = new SimpleStringProperty("${_self.toString()}")

  val sortProperty       = new SimpleStringProperty("")
  val shouldSortProperty = new SimpleBooleanProperty(true)

  val filterPromptProperty = new SimpleStringProperty("type to filter")
  val filter               = new SimpleObjectProperty(new SFXDataListFilter[S](this))

  protected override def createDefaultSkin: Skin[SFXDataListView[S]] =
    new SFXDataListViewSkin[S](this)

  override def getUserAgentStylesheet: String = css

  def setItems(values: Iterable[S]): Unit =
    items.set(sortedItems(values))

  def remove(bean: SFXBean[S]): Unit =
    items.get.remove(bean)

  def add(bean: SFXBean[S]): Unit =
    items.get.add(bean)

  def getItems: ObservableList[SFXBean[S]] = items.get

  footer.addListener { (_, _, _) =>
    if (showFooter.get)
      footerLabel.get.setText(footerTextProperty.get.format(filter.get.filterResult.size, filter.get.itemValues.size))
    filter.get.filterResult.addChangeListener { _ =>
      if (showFooter.get)
        footerLabel.get.setText(footerTextProperty.get.format(filter.get.filterResult.size, filter.get.itemValues.size))
    }
  }

  private def sortedItems(values: ObservableList[SFXBean[S]]): ObservableList[SFXBean[S]] = {
    var result = values

    if (shouldSortProperty.get) {
      val sortKey = {
        if (sortProperty.get == null || sortProperty.get.isEmpty)
          cellProperty.get
        else
          sortProperty.get
      }
      result = values.asScala.sortBy(f => "" + f.getValue(sortKey))
    }
    result
  }

}

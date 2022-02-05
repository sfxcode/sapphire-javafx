package com.sfxcode.sapphire.javafx.filter

import com.sfxcode.sapphire.javafx.value.SFXBean
import com.sfxcode.sapphire.javafx.control.SFXDataListView
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import javafx.scene.control.{ ListView, TextField }
import com.sfxcode.sapphire.javafx.SFXCollectionExtensions._

class SFXDataListFilter[S <: AnyRef](dataList: SFXDataListView[S])
  extends SFXDataFilter[S](dataList.items, dataList.header) {
  var sortFiltered = true

  var searchField: TextField = addSearchField(dataList.cellProperty.get)
  searchField.setPromptText(dataList.filterPromptProperty.get)

  dataList.filterPromptProperty.addListener((_, oldValue, newValue) => searchField.setPromptText(newValue))

  dataList.cellProperty.addListener((_, oldValue, newValue) => cellPropertyChanged(oldValue, newValue))

  def cellPropertyChanged(oldValue: String, newValue: String): Unit = {
    controlList.clear()
    controlFilterMap.clear()
    controlFilterPropertyMap.clear()
    valueMap.clear()

    filterControlNameMapping.clear()
    filterNameControlMapping.clear()
    searchField.setText("")

    if (dataList.header.get != null)
      dataList.header.get.getChildren.remove(searchField)

    searchField = addSearchField(dataList.cellProperty.get)
    searchField.setPromptText(dataList.filterPromptProperty.get)

  }

  filterResult.addChangeListener { _ =>
    dataList.listView.getItems.clear()
    if (!filterResult.isEmpty)
      filterResult.foreach(v => dataList.listView.getItems.add(v))
  }

  filter()

  override def itemsHasChanged(): Unit = {
    super.itemsHasChanged()
    listView.autosize()
    listView.layout()
  }

  def reload(shouldReset: Boolean = false): Unit = {
    listView.setItems(null)
    if (shouldReset)
      reset()
    else
      filter()
  }

  def listView: ListView[SFXBean[S]] = dataList.listView

  def selectedBean: SFXBean[S] = listView.getSelectionModel.selectedItemProperty.get

  def selectedItem: ReadOnlyObjectProperty[SFXBean[S]] = listView.getSelectionModel.selectedItemProperty

  def selectedItems: ObservableList[SFXBean[S]] = listView.getSelectionModel.getSelectedItems

}

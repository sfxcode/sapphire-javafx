package com.sfxcode.sapphire.javafx.showcase.controller.table

import com.sfxcode.sapphire.javafx.value.{ SFXBean, SFXBeanConversions }
import com.sfxcode.sapphire.javafx.controller.SFXDataTableController
import com.sfxcode.sapphire.javafx.filter.SFXDataTableFilter
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.{ Friend, PersonDatabase }
import javafx.collections.ObservableList
import javafx.event.ActionEvent

import scala.reflect._

class FriendTableController extends SFXDataTableController with BaseController with SFXBeanConversions {

  type R = Friend

  def ct: ClassTag[Friend] = classTag[R]

  def items: ObservableList[SFXBean[Friend]] = PersonDatabase.friends

  override def willGainVisibility(): Unit =
    super.willGainVisibility()

  override def initTable(tableFilter: SFXDataTableFilter[R]): Unit = {
    super.initTable(tableFilter)
    tableFilter.addSearchField("nameFilter", "name").setPromptText("Name")
  }

  def actionAddItem(event: ActionEvent): Unit =
    tableFilter.itemValues.add(items.head)

  def actionAddItems(event: ActionEvent): Unit =
    tableFilter.itemValues.addAll(items)

  def actionRemoveAllItems(event: ActionEvent): Unit =
    tableFilter.itemValues.clear()

  def actionReplaceItems(event: ActionEvent): Unit =
    tableFilter.itemsProperty.set(items)
}

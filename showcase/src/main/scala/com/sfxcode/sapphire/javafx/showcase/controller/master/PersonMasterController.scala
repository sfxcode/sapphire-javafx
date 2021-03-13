package com.sfxcode.sapphire.javafx.showcase.controller.master

import com.sfxcode.sapphire.javafx.value.{SFXBean, SFXBeanConversions}
import com.sfxcode.sapphire.javafx.controller.{SFXBaseDetailController, SFXBaseMasterController}
import com.sfxcode.sapphire.javafx.showcase.model.{Person, PersonDatabase}
import com.sfxcode.sapphire.javafx.filter.SFXDataTableFilter
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import javafx.collections.ObservableList

import scala.reflect._

class PersonMasterController extends SFXBaseMasterController with BaseController with SFXBeanConversions {
  lazy val personDetailController = getController[PersonDetailController]()

  type R = Person

  def ct: ClassTag[Person] = classTag[R]

  def items: ObservableList[SFXBean[Person]] = PersonDatabase.smallPersonTable

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    detailController = Some(personDetailController)
  }

  override def initTable(tableFilter: SFXDataTableFilter[R]): Unit = {
    super.initTable(tableFilter)
    tableFilter.hideColumn("metaData")
    tableFilter.addSearchField("nameFilter", "name").setPromptText("Name")

    tableFilter.hideColumn("tags", "friends", "about", "guid", "picture")

    tableFilter.addSearchField("addressFilter", "address").setPromptText("Address")
    tableFilter.addSearchBox("genderFilter", "gender", "male/female")
    tableFilter.addSearchBox("fruitFilter", "favoriteFruit", "all fruits")
  }

  override def navigateToDetailController(detailController: SFXBaseDetailController): Unit =
    updateShowcaseContent(detailController)

}

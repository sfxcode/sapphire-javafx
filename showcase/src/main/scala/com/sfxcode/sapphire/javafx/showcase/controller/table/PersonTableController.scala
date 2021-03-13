package com.sfxcode.sapphire.javafx.showcase.controller.table

import com.sfxcode.sapphire.javafx.value.{ SFXBean, SFXBeanConversions }
import com.sfxcode.sapphire.javafx.controller.SFXDataTableController
import com.sfxcode.sapphire.javafx.showcase.model.{ Person, PersonDatabase }
import com.sfxcode.sapphire.javafx.filter.SFXDataTableFilter
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import better.files.{ File, Resource }
import com.sfxcode.sapphire.data.report.{ AdapterDataSource, PdfExporter }
import javafx.scene.control.SelectionMode

import sys.process._
import scala.language.postfixOps
import scala.reflect._

class PersonTableController extends SFXDataTableController with BaseController with SFXBeanConversions {

  type R = Person

  def ct: ClassTag[Person] = classTag[R]

  def items: ObservableList[SFXBean[Person]] = PersonDatabase.bigPersonTable

  override def initTable(tableFilter: SFXDataTableFilter[R]): Unit = {
    super.initTable(tableFilter)
    table.getSelectionModel.setSelectionMode(SelectionMode.MULTIPLE)

    tableFilter.addSearchField("nameFilter", "name").setPromptText("Name")

    tableFilter.hideColumn("tags", "friends", "about", "guid", "picture")

    // #DataFilter
    tableFilter.addSearchField("addressFilter", "address").setPromptText("Address")
    tableFilter.addSearchBox("genderFilter", "gender", "male/female")
    tableFilter.addSearchBox("fruitFilter", "favoriteFruit", "all fruits")
    // #DataFilter
  }

  def actionExport(event: ActionEvent): Unit =
    if (tableFilter.selectedItems.nonEmpty) {
      // #Export
      // create report
      val exporter = PdfExporter(Resource.getUrl("report/personTable.jrxml"))
      val exportResult = exporter.exportReport(
        File.newTemporaryFile(),
        Map("text" -> "All Persons"),
        AdapterDataSource.fromList[Person](tableFilter.selectedItems.toList))
      // open report
      if (exportResult.completed)
        if (System.getProperty("os.name").contains("Mac"))
          "open %s".format(exportResult.exportFile.pathAsString) !
        else
          "xdg-open %s".format(exportResult.exportFile.pathAsString) !
      // #Export

    } else
      logger.warn("empty selection")

}

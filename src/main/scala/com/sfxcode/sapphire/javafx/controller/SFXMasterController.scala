package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.javafx.control.event.SFXEventHelper
import com.sfxcode.sapphire.javafx.filter.SFXDataTableFilter
import com.sfxcode.sapphire.javafx.value.SFXBean
import javafx.scene.Node
import javafx.scene.control.TableRow
import javafx.scene.control.skin.{ TableColumnHeader, TableHeaderRow }

abstract class SFXMasterController extends SFXDataTableController with SFXEventHelper {

  var detailController: Option[SFXDetailController] = None
  var lastSelected: Int = 0

  override def initTable(filter: SFXDataTableFilter[R]): Unit = {
    super.initTable(filter)
    table.setOnMouseClicked {
      event =>
        val clickCount = event.getClickCount
        if (clickCount == 2) {
          val isHeaderClicked = isEventTargetTableHeader(event.getTarget)
          if (!isHeaderClicked) {
            onDoubleClick(filter.selectedBean)
            lastSelected = filter.getTable.getSelectionModel.selectedIndexProperty().intValue()
          }
        }
    }
  }

  override def didGainVisibilityFirstTime(): Unit =
    super.didGainVisibilityFirstTime()

  override def didGainVisibility(): Unit = {
    super.didGainVisibility()
    table.getSelectionModel.selectFirst()
    table.requestFocus()
  }

  def onDoubleClick(bean: SFXBean[R]): Unit = {
    logger.debug("double clicked %s".format(bean))
    detailController.foreach {
      detailController =>
        navigateToDetailController(detailController)
        detailController.masterTableController = Some(this)
        updateDetailBean(bean)
    }
  }

  def updateDetailBean(bean: SFXBean[R]): Unit =
    detailController.foreach {
      detailController =>
        detailController.updateBean(bean.asInstanceOf[SFXBean[detailController.R]])
    }

  def navigateToDetailController(detailController: SFXDetailController): Unit

}

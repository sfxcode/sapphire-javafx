package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.javafx.value.FXBean
import com.sfxcode.sapphire.javafx.filter.DataTableFilter

abstract class BaseMasterController extends DataTableController {

  var detailController: Option[BaseDetailController] = None
  var lastSelected: Int                              = 0

  override def initTable(filter: DataTableFilter[R]): Unit = {
    super.initTable(filter)
    table.setOnMouseClicked { event =>
      if (event.getClickCount == 2) {
        onDoubleClick(filter.selectedBean)
        lastSelected = filter.getTable.getSelectionModel.selectedIndexProperty().intValue()
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

  def onDoubleClick(bean: FXBean[R]): Unit = {
    logger.debug("double clicked %s".format(bean))
    detailController.foreach { detailController =>
      navigateToDetailController(detailController)
      detailController.masterTableController = Some(this)
      updateDetailBean(bean)
    }
  }

  def updateDetailBean(bean: FXBean[R]): Unit =
    detailController.foreach { detailController =>
      detailController.updateBean(bean.asInstanceOf[FXBean[detailController.R]])
    }

  def navigateToDetailController(detailController: BaseDetailController): Unit

}

package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.javafx.filter.DataTableFilter
import com.sfxcode.sapphire.javafx.value.FXBean
import javafx.scene.Node
import javafx.scene.control.TableRow
import javafx.scene.control.skin.TableHeaderRow

abstract class BaseMasterController extends DataTableController {

  var detailController: Option[BaseDetailController] = None
  var lastSelected: Int                              = 0

  override def initTable(filter: DataTableFilter[R]): Unit = {
    super.initTable(filter)
    table.setOnMouseClicked { event =>
      if (event.getClickCount == 2 && isEventTargetTableRow(event.getTarget)) {
        onDoubleClick(filter.selectedBean)
        lastSelected = filter.getTable.getSelectionModel.selectedIndexProperty().intValue()
      }
    }
  }

  def isEventTargetTableRow(target: Any): Boolean = {
    val node = target.asInstanceOf[Node]
    node match {
      case _: TableRow[_]    => true
      case _: TableHeaderRow => false
      case _ =>
        node.getParent match {
          case _: TableRow[_]    => true
          case _: TableHeaderRow => false
          case _                 => false
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

package com.sfxcode.sapphire.javafx.control.event

import javafx.scene.Node
import javafx.scene.control.TableRow
import javafx.scene.control.skin.TableHeaderRow

trait SFXEventHelper {

  def isEventTargetTableHeader(eventTarget: Any): Boolean = {
    var node = eventTarget.asInstanceOf[Node]
    while (node.getParent != null && !node.isInstanceOf[TableHeaderRow] && !node.isInstanceOf[TableRow[_]])
      node = node.getParent
    node.isInstanceOf[TableHeaderRow]
  }
}

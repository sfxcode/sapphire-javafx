package com.sfxcode.sapphire.javafx.control.event

import javafx.event.ActionEvent
import javafx.scene.Node
import javafx.scene.control._

trait SFXActionEvents {

  def selectionFromActionEvent[S <: Any](event: ActionEvent): Option[S] =
    actionSource[Node](event) match {
      case tableView: TableView[S] => Some(tableView.getSelectionModel.getSelectedItem)
      case treeTableView: TreeTableView[S] => Some(treeTableView.getSelectionModel.getSelectedItem.getValue)
      case listView: ListView[S] => Some(listView.getSelectionModel.getSelectedItem)
      case comboBox: ComboBox[S] => Some(comboBox.getSelectionModel.getSelectedItem)
      case choiceBox: ChoiceBox[S] => Some(choiceBox.getSelectionModel.getSelectedItem)
      case _ => None
    }

  def actionSource[T <: Node](event: ActionEvent): T = event.getSource.asInstanceOf[T]
}

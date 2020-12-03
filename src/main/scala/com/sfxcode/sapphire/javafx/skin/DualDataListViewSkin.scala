package com.sfxcode.sapphire.javafx.skin

import javafx.beans.binding.Bindings
import javafx.scene.control.SelectionMode._
import javafx.scene.control.{ Button, MultipleSelectionModel, SkinBase }
import com.sfxcode.sapphire.javafx.value.{ BeanConversions, FXBean }
import com.sfxcode.sapphire.javafx.control.{ DataListView, DualDataListView }
import javafx.geometry.Pos
import javafx.scene.layout._
import com.sfxcode.sapphire.javafx.control.IconTools._
import javafx.collections.{ FXCollections, ObservableList }
import com.sfxcode.sapphire.javafx.CollectionExtensions._
import com.sfxcode.sapphire.javafx.ConfigValues
import com.sfxcode.sapphire.javafx.scene.SceneExtensions._

class DualDataListViewSkin[S <: AnyRef](view: DualDataListView[S])
  extends SkinBase[DualDataListView[S]](view)
  with BeanConversions
  with ConfigValues {
  val IconConfigPath = "com.sfxcode.sapphire.javafx.skin.icon."

  val contentGridPane: GridPane = new GridPane() {
    getStyleClass.add("content-grid")
  }

  val buttonMoveToTarget: Button = decoratedFontIconButton(
    configStringValue(IconConfigPath + "right", "fa-angle-right"))
  val buttonMoveToTargetAll: Button = decoratedFontIconButton(
    configStringValue(IconConfigPath + "double-right", "fa-angle-double-right"))
  val buttonMoveToSource: Button = decoratedFontIconButton(configStringValue(IconConfigPath + "left", "fa-angle-left"))
  val buttonMoveToSourceAll: Button = decoratedFontIconButton(
    configStringValue(IconConfigPath + "double-left", "fa-angle-double-left"))

  buttonMoveToTarget.setOnAction(_ => moveToTarget())
  buttonMoveToSource.setOnAction(_ => moveToSource())
  buttonMoveToTargetAll.setOnAction(_ => moveAllToTarget())
  buttonMoveToSourceAll.setOnAction(_ => moveAllToSource())

  def leftItems: ObservableList[FXBean[S]] = view.leftDataListView.getItems

  def leftSelectionModel: MultipleSelectionModel[FXBean[S]] = view.leftDataListView.listView.getSelectionModel

  def rightItems: ObservableList[FXBean[S]] = view.rightDataListView.getItems

  def rightSelectionModel: MultipleSelectionModel[FXBean[S]] = view.rightDataListView.listView.getSelectionModel

  leftSelectionModel.setSelectionMode(MULTIPLE)
  rightSelectionModel.setSelectionMode(MULTIPLE)

  leftItems.addChangeListener(_ => bindButtons())

  rightItems.addChangeListener(_ => bindButtons())

  leftSelectionModel.getSelectedItems.addChangeListener(_ => bindButtons())

  rightSelectionModel.getSelectedItems.addChangeListener(_ => bindButtons())

  def bindButtons(): Unit = {
    buttonMoveToTargetAll.disableProperty.bind(Bindings.isEmpty(leftSelectionModel.getSelectedItems))
    buttonMoveToSourceAll.disableProperty.bind(Bindings.isEmpty(rightSelectionModel.getSelectedItems))

    buttonMoveToTarget.disableProperty.bind(Bindings.isEmpty(leftSelectionModel.getSelectedItems))
    buttonMoveToSource.disableProperty.bind(Bindings.isEmpty(rightSelectionModel.getSelectedItems))
  }

  view.leftDataListView.listView.onPrimaryButtonDoubleClicked(moveToTarget())
  view.rightDataListView.listView.onPrimaryButtonDoubleClicked(moveToSource())

  val buttonBox = new VBox
  buttonBox.getStyleClass.add("button-box")
  buttonBox.setAlignment(Pos.CENTER)
  buttonBox.setSpacing(5)
  buttonBox.setFillWidth(true)
  buttonBox.getChildren.addAll(buttonMoveToTarget, buttonMoveToTargetAll, buttonMoveToSource, buttonMoveToSourceAll)

  getChildren.add(contentGridPane)
  bindButtons()
  updateView()

  def updateView(): Unit = {
    addGridPaneConstraints()
    contentGridPane.add(view.leftDataListView, 0, 0)
    contentGridPane.add(buttonBox, 1, 0)
    contentGridPane.add(view.rightDataListView, 2, 0)
  }

  def addGridPaneConstraints(): Unit = {
    val row = new RowConstraints()
    row.setFillHeight(true)
    row.setVgrow(Priority.NEVER)
    contentGridPane.getRowConstraints.add(row)

    val col1 = new ColumnConstraints

    col1.setFillWidth(true)
    col1.setHgrow(Priority.ALWAYS)
    col1.setMaxWidth(Double.MaxValue)
    col1.setPrefWidth(200)

    val col2 = new ColumnConstraints
    col2.setFillWidth(true)
    col2.setHgrow(Priority.NEVER)
    col2.setMaxWidth(50)
    col2.setMinWidth(50)

    val col3 = new ColumnConstraints
    col3.setFillWidth(true)
    col3.setHgrow(Priority.ALWAYS)
    col3.setMaxWidth(Double.MaxValue)
    col3.setPrefWidth(200)

    contentGridPane.getColumnConstraints.addAll(col1, col2, col3)
  }

  private def moveToTarget(): Unit = {
    move(
      view.leftDataListView,
      view.rightDataListView,
      FXCollections.observableArrayList(leftSelectionModel.getSelectedItem))
    leftSelectionModel.clearSelection()
  }

  private def moveToSource(): Unit = {
    move(
      view.rightDataListView,
      view.leftDataListView,
      FXCollections.observableArrayList(rightSelectionModel.getSelectedItem))
    rightSelectionModel.clearSelection()
  }

  private def moveAllToTarget(): Unit = {
    move(view.leftDataListView, view.rightDataListView, leftSelectionModel.getSelectedItems)
    leftSelectionModel.clearSelection()
  }

  private def moveAllToSource(): Unit = {
    move(view.rightDataListView, view.leftDataListView, rightSelectionModel.getSelectedItems)
    rightSelectionModel.clearSelection()
  }

  def move(source: DataListView[S], target: DataListView[S], items: ObservableList[FXBean[S]]): Unit = {
    val sourceItems = FXCollections.observableArrayList(source.getItems)
    sourceItems.removeAll(items)
    val targetItems: ObservableList[FXBean[S]] = FXCollections.observableArrayList(target.getItems)
    targetItems.addAll(items)
    source.setItems(sourceItems)
    target.setItems(targetItems)
  }

}

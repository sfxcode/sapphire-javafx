package com.sfxcode.sapphire.javafx.demo.tutorial.controller.app

import com.sfxcode.sapphire.javafx.demo.tutorial.controller.base.AbstractViewController
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.chart.PieChart

class WorkspaceController extends AbstractViewController {

  val pieChartDataBuffer: ObservableList[PieChart.Data] = FXCollections.observableArrayList(
    new PieChart.Data("Grapefruit", 20),
    new PieChart.Data("Oranges", 30),
    new PieChart.Data("Plums", 10),
    new PieChart.Data("Apples", 40)
  )
  @FXML
  var chart: javafx.scene.chart.PieChart = _

  override def didInitialize(): Unit = {
    super.didInitialize()
    chart.setData(pieChartDataBuffer)
  }
}

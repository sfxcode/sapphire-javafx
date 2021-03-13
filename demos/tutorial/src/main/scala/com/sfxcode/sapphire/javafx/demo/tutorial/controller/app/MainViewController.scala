package com.sfxcode.sapphire.javafx.demo.tutorial.controller.app

import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.sfxcode.sapphire.javafx.demo.tutorial.controller.page.{ ChartPageController, PersonPageController }
import com.sfxcode.sapphire.javafx.scene.SFXContentManager
import com.typesafe.scalalogging.LazyLogging
import javafx.fxml.FXML
import javafx.scene.layout.Pane

class MainViewController extends SFXViewController with LazyLogging {

  // #controllerLoading
  lazy val workspaceController: WorkspaceController =
    getController[WorkspaceController]()
  lazy val barChartController: ChartPageController =
    getController[ChartPageController]()
  lazy val personController: PersonPageController =
    getController[PersonPageController]()
  lazy val navigationController: NavigationController =
    getController[NavigationController]()
  lazy val statusBarController: StatusBarController =
    new StatusBarController()
  // #controllerLoading

  // #fxmlBinding
  @FXML
  var workspacePane: Pane = _
  @FXML
  var statusPane: Pane = _
  @FXML
  var navigationPane: Pane = _
  // #fxmlBinding

  // #contentManager
  var workspaceManager: SFXContentManager = _
  var navigationManager: SFXContentManager = _
  var statusBarManager: SFXContentManager = _
  // #contentManager

  // #didGainVisibilityFirstTime
  override def didGainVisibilityFirstTime() {
    super.didGainVisibility()
    navigationManager = SFXContentManager(navigationPane, this, navigationController)
    statusBarManager = SFXContentManager(statusPane, this, statusBarController)
    workspaceManager = SFXContentManager(workspacePane, this, workspaceController)
  }
  // #didGainVisibilityFirstTime

}

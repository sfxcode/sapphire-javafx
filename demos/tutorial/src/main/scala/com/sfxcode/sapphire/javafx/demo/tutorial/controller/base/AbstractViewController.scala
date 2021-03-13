package com.sfxcode.sapphire.javafx.demo.tutorial.controller.base

import com.sfxcode.sapphire.javafx.application.SFXApplicationEnvironment
import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.sfxcode.sapphire.javafx.demo.tutorial.ApplicationController
import com.sfxcode.sapphire.javafx.demo.tutorial.controller.app.{ MainViewController, StatusBarController }
import com.sfxcode.sapphire.javafx.scene.SFXContentManager
import com.typesafe.scalalogging.LazyLogging

abstract class AbstractViewController extends SFXViewController with LazyLogging {

  override def didGainVisibility(): Unit =
    statusBarController.statusLabel.setText("%s loaded".format(getClass.getSimpleName))

  // load applicationController by Application Environment
  def applicationController: ApplicationController =
    SFXApplicationEnvironment.applicationController[ApplicationController]

  // load applicationController by Expression
  def applicationControllerByExpression: ApplicationController = {
    val maybeController = registeredBean[ApplicationController]
    var result = maybeController.get
    result
  }

  def mainViewController: MainViewController = applicationController.mainViewController

  def statusBarController: StatusBarController = mainViewController.statusBarController

  def workspaceManager: SFXContentManager = mainViewController.workspaceManager

}

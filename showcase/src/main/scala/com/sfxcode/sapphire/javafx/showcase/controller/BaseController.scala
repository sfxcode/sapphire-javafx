package com.sfxcode.sapphire.javafx.showcase.controller

import com.sfxcode.sapphire.javafx.application.ApplicationEnvironment
import com.sfxcode.sapphire.javafx.controller.ViewController
import com.sfxcode.sapphire.javafx.showcase.ApplicationController

trait BaseController extends ViewController {

  def applicationController: ApplicationController = ApplicationEnvironment.applicationController[ApplicationController]

  def showcaseController: ShowcaseViewController = applicationController.showcaseController

  def updateShowcaseContent(controller: ViewController): Unit =
    showcaseController.updateShowcaseContent(controller)
}

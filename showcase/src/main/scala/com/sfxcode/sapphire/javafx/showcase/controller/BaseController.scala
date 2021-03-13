package com.sfxcode.sapphire.javafx.showcase.controller

import com.sfxcode.sapphire.javafx.application.SFXApplicationEnvironment
import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.sfxcode.sapphire.javafx.showcase.ApplicationController

trait BaseController extends SFXViewController {

  def applicationController: ApplicationController =
    SFXApplicationEnvironment.applicationController[ApplicationController]

  def showcaseController: ShowcaseViewController = applicationController.showcaseController

  def updateShowcaseContent(controller: SFXViewController): Unit =
    showcaseController.updateShowcaseContent(controller)
}

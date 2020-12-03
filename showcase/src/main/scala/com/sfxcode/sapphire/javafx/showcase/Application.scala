package com.sfxcode.sapphire.javafx.showcase

import com.sfxcode.sapphire.javafx.application.BaseApplication
import com.sfxcode.sapphire.javafx.controller.BaseApplicationController
import com.sfxcode.sapphire.javafx.scene.ExtensionResolver
import com.sfxcode.sapphire.javafx.showcase.controller.ShowcaseViewController
import com.typesafe.scalalogging.LazyLogging

object Application extends BaseApplication {
  override def title: String = "Sapphire Extension Showcase"

  override val applicationController: BaseApplicationController = new ApplicationController
}

class ApplicationController extends BaseApplicationController with LazyLogging {

  lazy val showcaseController: ShowcaseViewController = getController[ShowcaseViewController]()

  // #ExtensionResolver
  def applicationDidLaunch() {
    logger.debug("start " + this)
    ExtensionResolver.add()
    replaceSceneContent(showcaseController)
  }
  // #ExtensionResolver

}

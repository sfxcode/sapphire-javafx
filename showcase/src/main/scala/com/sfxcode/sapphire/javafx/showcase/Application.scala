package com.sfxcode.sapphire.javafx.showcase

import com.sfxcode.sapphire.javafx.application.SFXApplication
import com.sfxcode.sapphire.javafx.controller.SFXApplicationController
import com.sfxcode.sapphire.javafx.scene.SFXExtensionResolver
import com.sfxcode.sapphire.javafx.showcase.controller.ShowcaseViewController
import com.typesafe.scalalogging.LazyLogging

object Application extends SFXApplication {
  override def title: String = "Sapphire Extension Showcase"

  override val applicationController: SFXApplicationController = new ApplicationController
}

class ApplicationController extends SFXApplicationController with LazyLogging {

  lazy val showcaseController: ShowcaseViewController = getController[ShowcaseViewController]()

  // #ExtensionResolver
  def applicationDidLaunch() {
    logger.debug("start " + this)
    SFXExtensionResolver.add()
    replaceSceneContent(showcaseController)
  }
  // #ExtensionResolver

}

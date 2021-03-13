package com.sfxcode.sapphire.javafx.showcase

import com.sfxcode.sapphire.javafx.application.SFXBaseApplication
import com.sfxcode.sapphire.javafx.controller.SFXBaseApplicationController
import com.sfxcode.sapphire.javafx.scene.SFXExtensionResolver
import com.sfxcode.sapphire.javafx.showcase.controller.ShowcaseViewController
import com.typesafe.scalalogging.LazyLogging

object Application extends SFXBaseApplication {
  override def title: String = "Sapphire Extension Showcase"

  override val applicationController: SFXBaseApplicationController = new ApplicationController
}

class ApplicationController extends SFXBaseApplicationController with LazyLogging {

  lazy val showcaseController: ShowcaseViewController = getController[ShowcaseViewController]()

  // #ExtensionResolver
  def applicationDidLaunch() {
    logger.debug("start " + this)
    SFXExtensionResolver.add()
    replaceSceneContent(showcaseController)
  }
  // #ExtensionResolver

}

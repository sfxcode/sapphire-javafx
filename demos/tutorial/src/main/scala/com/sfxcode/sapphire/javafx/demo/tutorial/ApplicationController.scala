package com.sfxcode.sapphire.javafx.demo.tutorial

import java.util.{ Locale, ResourceBundle }
import com.sfxcode.sapphire.javafx.application.SFXApplicationEnvironment
import com.sfxcode.sapphire.javafx.controller.SFXApplicationController
import com.sfxcode.sapphire.javafx.demo.tutorial.controller.app.MainViewController

class ApplicationController extends SFXApplicationController {

  var mainViewController: MainViewController = _

  def applicationDidLaunch() {
    logger.info("start " + this)
    // #Resources
    SFXApplicationEnvironment.loadResourceBundle("bundles/application")
    // #Resources
    reload()
  }

  def reload(): Unit = {
    // Styling
    reloadStyles()
    // Resources
    SFXApplicationEnvironment.clearResourceBundleCache()
    SFXApplicationEnvironment.loadResourceBundle("bundles/application")
    // FXML
    mainViewController = getController[MainViewController]()
    replaceSceneContent(mainViewController)
    // do some other stuff
  }

  def applicationName: ApplicationName =
    ApplicationName(configStringValue("application.name"))

  // #CustomBundle
  // only example values ...
  override def resourceBundleForView(viewPath: String): ResourceBundle =
    if (viewPath.contains("mySpecialViewName")) {
      val path = "myCustomResourcePath"
      val classLoader = Thread.currentThread().getContextClassLoader
      ResourceBundle.getBundle(path, Locale.getDefault(), classLoader)
    } else
      super.resourceBundleForView(viewPath) // =  applicationEnvironment.resourceBundle

  // #CustomBundle

}

case class ApplicationName(name: String)

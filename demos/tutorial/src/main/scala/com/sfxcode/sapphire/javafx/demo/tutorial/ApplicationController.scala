package com.sfxcode.sapphire.javafx.demo.tutorial

import java.util.{Locale, ResourceBundle}
import com.sfxcode.sapphire.javafx.application.ApplicationEnvironment
import com.sfxcode.sapphire.javafx.controller.BaseApplicationController
import com.sfxcode.sapphire.javafx.demo.tutorial.controller.app.MainViewController

class ApplicationController extends BaseApplicationController {

  var mainViewController: MainViewController = _

  def applicationDidLaunch() {
    logger.info("start " + this)
    // #Resources
    ApplicationEnvironment.loadResourceBundle("bundles/application")
    // #Resources
    reload()
  }

  def reload(): Unit = {
    // Styling
    reloadStyles()
    // Resources
    ApplicationEnvironment.clearResourceBundleCache()
    ApplicationEnvironment.loadResourceBundle("bundles/application")
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
      val path        = "myCustomResourcePath"
      val classLoader = Thread.currentThread().getContextClassLoader
      ResourceBundle.getBundle(path, Locale.getDefault(), classLoader)
    }
    else
      super.resourceBundleForView(viewPath) // =  applicationEnvironment.resourceBundle

  // #CustomBundle

}

case class ApplicationName(name: String)

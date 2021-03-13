package com.sfxcode.sapphire.javafx.demo.issues

import com.sfxcode.sapphire.javafx.application.{ SFXApplication, SFXApplicationEnvironment }
import com.sfxcode.sapphire.javafx.controller.SFXApplicationController
import com.sfxcode.sapphire.javafx.demo.issues.controller.IssueTrackingLiteController
import com.sfxcode.sapphire.javafx.demo.issues.deltaspike._
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Named

case class EmptyName(name: String)

// #Application

object Application extends SFXApplication with DeltaspikeBeanResolver {

  DeltaspikeLauncher.init()
  SFXApplicationEnvironment.documentLoader = getBean[DeltaspikeDocumentLoader]()

  override val applicationController: SFXApplicationController = getBean[ApplicationController]()
}
// #Application

// #ApplicationController

@Named
@ApplicationScoped
class ApplicationController extends SFXApplicationController {

  lazy val mainController: IssueTrackingLiteController = getController[IssueTrackingLiteController]()

  def applicationDidLaunch() {
    replaceSceneContent(mainController)
  }

  // CDI Prducer Method
  @Produces
  def emptyName: EmptyName =
    EmptyName("New Issue")

}

// #ApplicationController

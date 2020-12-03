package com.sfxcode.sapphire.javafx.demo.issues

import com.sfxcode.sapphire.javafx.application.{ApplicationEnvironment, BaseApplication}
import com.sfxcode.sapphire.javafx.controller.BaseApplicationController
import com.sfxcode.sapphire.javafx.demo.issues.controller.IssueTrackingLiteController
import com.sfxcode.sapphire.javafx.demo.issues.deltaspike._
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Named

case class EmptyName(name: String)

// #Application

object Application extends BaseApplication with DeltaspikeBeanResolver {

  DeltaspikeLauncher.init()
  ApplicationEnvironment.documentLoader = getBean[DeltaspikeDocumentLoader]()

  override val applicationController: BaseApplicationController = getBean[ApplicationController]()
}
// #Application

// #ApplicationController

@Named
@ApplicationScoped
class ApplicationController extends BaseApplicationController {

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

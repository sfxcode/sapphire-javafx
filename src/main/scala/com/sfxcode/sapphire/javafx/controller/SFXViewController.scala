package com.sfxcode.sapphire.javafx.controller

import java.net.URL
import java.util.ResourceBundle

import com.sfxcode.sapphire.javafx.SFXCollectionExtensions._
import com.sfxcode.sapphire.data.el._
import com.sfxcode.sapphire.javafx.control.event.SFXActionEvents
import com.sfxcode.sapphire.javafx.fxml.FxmlLoading
import com.sfxcode.sapphire.javafx.scene.SFXNodeLocator
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.{ FXCollections, ObservableList }
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

import scala.language.implicitConversions

abstract class SFXViewController
  extends FxmlLoading
  with SFXActionEvents
  with Initializable
  with Expressions
  with LazyLogging
  with SFXNodeLocator {

  implicit def stringListToMap(list: List[String]): Map[String, String] = list.map(s => (s, s)).toMap

  implicit def simpleObjectPropertyToOption[T <: AnyRef](prop: SimpleObjectProperty[T]): Option[T] =
    Option[T](prop.get())

  val windowController = new SimpleObjectProperty[SFXWindowController]()

  val managedParent = new SimpleObjectProperty[SFXViewController]()
  protected val managedChildren: ObservableList[SFXViewController] =
    FXCollections.observableArrayList[SFXViewController]()
  protected val unmanagedChildren: ObservableList[SFXViewController] =
    FXCollections.observableArrayList[SFXViewController]()
  var gainedVisibility = false

  def stage: Stage = windowController.get.stage

  def scene: Scene = windowController.get.scene

  def parent: SFXViewController = managedParent.getValue

  def addChildViewController(viewController: SFXViewController): Unit =
    if (!managedChildren.contains(viewController)) {
      managedChildren.add(viewController)
    }

  def removeChildViewController(viewController: SFXViewController): Unit =
    if (!managedChildren.contains(viewController)) {
      managedChildren.remove(viewController)
    }

  // bean lifecycle

  registerBean(this)
  startup()

  def startup(): Unit = {}

  override def initialize(loc: URL, res: ResourceBundle): Unit = {
    location = Some(loc)
    resources = Some(res)
    didInitialize()
  }

  // controller lifecycle

  def didInitialize(): Unit = {}

  def canGainVisibility(): Boolean = true

  def willGainVisibility(): Unit = {
    managedChildren.foreach(controller => controller.willGainVisibility())
    unmanagedChildren.foreach(controller => controller.willGainVisibility())
  }

  def didGainVisibilityFirstTime(): Unit = {}

  def didGainVisibility(): Unit = {
    managedChildren.foreach(controller => controller.didGainVisibility())
    unmanagedChildren.foreach(controller => controller.didGainVisibility())
  }

  def shouldLooseVisibility: Boolean = true

  def willLooseVisibility(): Unit = {
    managedChildren.foreach(controller => controller.willLooseVisibility())
    unmanagedChildren.foreach(controller => controller.willLooseVisibility())
  }

  def didLooseVisibility(): Unit = {
    managedChildren.foreach(controller => controller.didLooseVisibility())
    unmanagedChildren.foreach(controller => controller.didLooseVisibility())
  }

  def updatePaneContent(pane: Pane, viewController: SFXViewController): Boolean =
    if (pane == null) {
      logger.warn("contentPane is NULL")
      false
    } else if (viewController == null) {
      logger.warn("viewController is NULL")
      false
    } else if (viewController.canGainVisibility())
      try {
        viewController.managedParent.setValue(this)
        viewController.windowController.set(windowController.get)
        viewController.willGainVisibility()
        pane.getChildren.add(viewController.rootPane)
        viewController.didGainVisibilityFirstTime()
        viewController.didGainVisibility()
        unmanagedChildren.add(viewController)
        true
      } catch {
        case e: Exception =>
          logger.error(e.getMessage, e)
          false
      }
    else
      false

  def stateMap: Map[String, Any] = Map[String, Any]()

  def updateFromStateMap(map: Map[String, Any]): Unit = {}

  def isActualSceneController: Boolean = this == actualSceneController

  def actualSceneController: SFXViewController = windowController.get.actualSceneController

  override def toString: String =
    "%s %s (fxml: %s, gainedVisibility: %s)".format(
      this.getClass.getSimpleName,
      hashCode(),
      isLoadedFromFXML,
      gainedVisibility)
}

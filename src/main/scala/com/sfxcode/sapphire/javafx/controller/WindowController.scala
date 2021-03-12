package com.sfxcode.sapphire.javafx.controller

import java.util.ResourceBundle
import com.sfxcode.sapphire.javafx.CollectionExtensions._
import com.sfxcode.sapphire.javafx.application.ApplicationEnvironment
import com.sfxcode.sapphire.data.el.Expressions
import com.sfxcode.sapphire.javafx.fxml.FxmlLoading
import com.sfxcode.sapphire.javafx.scene.NodeLocator
import com.typesafe.scalalogging.LazyLogging
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableMap
import javafx.scene.layout.StackPane
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

abstract class WindowController extends FxmlLoading with NodeLocator with Expressions with LazyLogging {
  val sceneMap: ObservableMap[Parent, Scene] = Map[Parent, Scene]()

  var stageProperty           = new SimpleObjectProperty[Stage]()
  var sceneProperty           = new SimpleObjectProperty[Scene]()
  var sceneControllerProperty = new SimpleObjectProperty[ViewController]()

  implicit def simpleObjectPropertyToOption[T <: AnyRef](prop: SimpleObjectProperty[T]): Option[T] =
    Option[T](prop.get())

  // bean lifecycle

  registerBean(this)
  startup()

  def startup() {}

  def shutdown() {}

  def setStage(stage: Stage): Unit = {
    stageProperty.set(stage)
    sceneProperty.set(createScene())
  }

  def name: String = getClass.getSimpleName

  def stage: Stage = stageProperty.getValue

  def scene: Scene = sceneProperty.getValue

  def resourceBundleForView(viewPath: String): ResourceBundle = ApplicationEnvironment.resourceBundle

  def isMainWindow: Boolean

  def replaceSceneContent(newController: ViewController, resize: Boolean = true) {
    val oldController = actualSceneController
    if (
      newController != null && newController != oldController && newController.canGainVisibility
      && (oldController == null || oldController.shouldLooseVisibility)
    ) {
      if (oldController != null)
        try oldController.willLooseVisibility()
        catch {
          case e: Exception => logger.error(e.getMessage, e)
        }
      try {
        newController.windowController.set(this)
        newController.willGainVisibility()
      }
      catch {
        case e: Exception => logger.error(e.getMessage, e)
      }
      replaceSceneContentWithNode(newController.rootPane, resize)
      sceneControllerProperty.set(newController)
      if (oldController != null)
        try oldController.didLooseVisibility()
        catch {
          case e: Exception => logger.error(e.getMessage, e)
        }
    }
    if (!newController.gainedVisibility) {
      try newController.didGainVisibilityFirstTime()
      catch {
        case e: Exception => logger.error(e.getMessage, e)
      }
      newController.gainedVisibility = true
    }

    try newController.didGainVisibility()
    catch {
      case e: Exception => logger.error(e.getMessage, e)
    }
  }

  def actualSceneController: ViewController = sceneControllerProperty.getValue

  protected def createScene(): Scene = new Scene(new StackPane())

  protected def replaceSceneContentWithNode(content: Parent, resize: Boolean = true) {
    scene.setRoot(content)
    stage.setScene(scene)
    if (resize)
      stage.sizeToScene()
  }

}

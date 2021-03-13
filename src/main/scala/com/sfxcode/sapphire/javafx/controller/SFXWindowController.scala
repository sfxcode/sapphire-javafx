package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.data.el.Expressions
import com.sfxcode.sapphire.javafx.SFXCollectionExtensions._
import com.sfxcode.sapphire.javafx.SFXLogging
import com.sfxcode.sapphire.javafx.application.SFXApplicationEnvironment
import com.sfxcode.sapphire.javafx.fxml.FxmlLoading
import com.sfxcode.sapphire.javafx.scene.SFXNodeLocator
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableMap
import javafx.scene.layout.StackPane
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import java.util.ResourceBundle

abstract class SFXWindowController extends FxmlLoading with SFXNodeLocator with Expressions with SFXLogging {
  val sceneMap: ObservableMap[Parent, Scene] = Map[Parent, Scene]()

  var stageProperty           = new SimpleObjectProperty[Stage]()
  var sceneProperty           = new SimpleObjectProperty[Scene]()
  var sceneControllerProperty = new SimpleObjectProperty[SFXViewController]()

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

  def resourceBundleForView(viewPath: String): ResourceBundle = SFXApplicationEnvironment.resourceBundle

  def isMainWindow: Boolean

  def replaceSceneContent(newController: SFXViewController, resize: Boolean = true) {
    val oldController = actualSceneController

    if (
      newController != null && newController != oldController && newController.canGainVisibility
      && (oldController == null || oldController.shouldLooseVisibility)
    ) {
      // old willLooseVisibility
      if (oldController != null) {
        withErrorLogging(oldController.willLooseVisibility())
      }
      // new willGainVisibility
      withErrorLogging({
        newController.windowController.set(this)
        newController.willGainVisibility()
      })
      // replace content
      replaceSceneContentWithNode(newController.rootPane, resize)
      sceneControllerProperty.set(newController)
      // old didLooseVisibility
      if (oldController != null) {
        withErrorLogging(oldController.didLooseVisibility())
      }
    }
    if (!newController.gainedVisibility) {
      // new didGainVisibilityFirstTime
      withErrorLogging(newController.didGainVisibilityFirstTime())
      newController.gainedVisibility = true
    }
    // new didGainVisibility
    withErrorLogging(newController.didGainVisibility())

  }

  def actualSceneController: SFXViewController = sceneControllerProperty.getValue

  protected def createScene(): Scene = new Scene(new StackPane())

  protected def replaceSceneContentWithNode(content: Parent, resize: Boolean = true) {
    scene.setRoot(content)
    stage.setScene(scene)

    if (!stage.isShowing) {
      stage.show()
    }

    if (resize) {
      stage.sizeToScene()
    }
  }

}

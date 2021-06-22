package com.sfxcode.sapphire.javafx.scene

import com.sfxcode.sapphire.javafx.SFXLogging
import com.sfxcode.sapphire.javafx.controller.SFXViewController
import javafx.beans.property.{SimpleBooleanProperty, SimpleIntegerProperty}
import javafx.scene.Node
import javafx.scene.layout.Pane

class SFXContentManager extends SFXLogging {
  val stackSize               = new SimpleIntegerProperty(0)
  private val controllerStack = ControllerStack(this)
  var useStack                = new SimpleBooleanProperty(false)

  var contentPane: Pane = _

  var parentController: SFXViewController = _
  var actualController: SFXViewController = _
  var lastController: SFXViewController   = _

  def addToStack(viewController: SFXViewController): Unit = {
    controllerStack.push(viewController)
    stackSize.setValue(controllerStack.size)
  }

  def loadFromStack(): Option[SFXViewController] = {
    val result = controllerStack.pop()
    stackSize.setValue(controllerStack.size)
    result
  }

  def loadFromStackUntil[T <: SFXViewController](): Option[T] = {
    val result = controllerStack.popUntil[T]()
    stackSize.setValue(controllerStack.size)
    result
  }

  def enableStack(): Unit = useStack.set(true)

  def disableStack(): Unit = useStack.set(false)

  def switchToLast(): Unit =
    updatePaneContent(lastController)

  def updateWithTransition(
      newController: SFXViewController,
      transition: SFXTransition = SFXEaseInTransition(),
      pushToStack: Boolean = true
  ): Unit =
    updatePaneContent(newController, Some(transition), pushToStack)

  def updatePaneContent(
      newController: SFXViewController,
      transition: Option[SFXTransition] = None,
      pushToStack: Boolean = true
  ): Unit = {

    val oldController         = actualController
    val isDifferentController = newController != oldController
    val canChange             = oldController == null || oldController.shouldLooseVisibility

    if (newController != null && isDifferentController && newController.canGainVisibility() && canChange) {

      if (oldController != null) {
        withErrorLogging(oldController.willLooseVisibility())
      }

      withErrorLogging({
        newController.windowController.set(parentController.windowController.getValue)
        newController.willGainVisibility()
      })

      switchController(newController, oldController, transition, pushToStack)
      if (!newController.gainedVisibility) {
        withErrorLogging(newController.didGainVisibilityFirstTime())
        newController.gainedVisibility = true
      }
      withErrorLogging({
        newController.didGainVisibility()
        parentController.addChildViewController(newController)
      })
      actualController = newController
    }
  }

  protected def switchController(
      newController: SFXViewController,
      oldController: SFXViewController,
      transition: Option[SFXTransition],
      pushToStack: Boolean
  ): Unit =
    if (oldController != null) {
      if (transition.isDefined) {
        val timeline = transition.get.createTimeline(newController.rootPane, oldController.rootPane)
        timeline.setOnFinished { _ =>
          removeOldController(oldController, pushToStack)
        }
        addNewController(newController)
        timeline.play()
      }
      else {
        addNewController(newController)
        removeOldController(oldController, pushToStack)
      }
    }
    else {
      addNewController(newController)
    }

  protected def addNewController(newController: SFXViewController): Unit =
    withErrorLogging({
      addPaneContent(newController.rootPane)
      newController.managedParent.setValue(parentController)
    })

  protected def removeOldController(oldController: SFXViewController, pushToStack: Boolean): Unit = {
    lastController = oldController
    if (useStack.getValue && pushToStack) {
      controllerStack.push(oldController)
    }
    removePaneContent(oldController.rootPane)
    oldController.managedParent.setValue(null)
    parentController.removeChildViewController(oldController)
    withErrorLogging(oldController.didLooseVisibility())
  }

  private def removePaneContent(node: Node): Unit =
    contentPane.getChildren.remove(node)

  private def addPaneContent(node: Node): Unit =
    if (!contentPane.getChildren.contains(node)) {
      contentPane.getChildren.add(node)
    }

}

object SFXContentManager {

  def apply(
      contentPane: Pane,
      parentController: SFXViewController,
      startController: SFXViewController = null
  ): SFXContentManager = {

    if (contentPane == null)
      throw new IllegalArgumentException("contentPane must not be NULL")

    if (parentController == null)
      throw new IllegalArgumentException("parentController must not be NULL")

    val result = new SFXContentManager
    result.contentPane = contentPane
    result.parentController = parentController
    result.updatePaneContent(startController)
    result
  }
}

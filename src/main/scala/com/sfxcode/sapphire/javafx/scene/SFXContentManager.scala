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

  def updatePaneContent(newController: SFXViewController, pushToStack: Boolean = true) {
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

      if (oldController != null) {
        removeOldController(oldController)
      }

      lastController = oldController
      if (useStack.getValue && pushToStack) {
        controllerStack.push(oldController)
      }

      addNewController(newController)

      actualController = newController
    }
  }

  private def removeOldController(oldController: SFXViewController): Unit = {
    removePaneContent(oldController.rootPane)
    oldController.managedParent.setValue(null)
    parentController.removeChildViewController(oldController)
    withErrorLogging(oldController.didLooseVisibility())
  }

  private def addNewController(newController: SFXViewController): Unit = {
    addPaneContent(newController.rootPane)
    newController.managedParent.setValue(parentController)

    if (!newController.gainedVisibility) {
      withErrorLogging(newController.didGainVisibilityFirstTime())
      newController.gainedVisibility = true
    }

    withErrorLogging({
      newController.didGainVisibility()
      parentController.addChildViewController(newController)
    })
  }

  private def removePaneContent(node: Node) {
    contentPane.getChildren.remove(node)
  }

  private def addPaneContent(node: Node) {
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

package com.sfxcode.sapphire.javafx.scene

import com.sfxcode.sapphire.javafx.controller.SFXViewController

import scala.collection.mutable

case class ControllerState(controller: SFXViewController, stateMap: Map[String, Any] = Map())

case class ControllerStack(contentManager: SFXContentManager) {
  private val stack = new mutable.Stack[ControllerState]()

  def push(viewController: SFXViewController): Unit =
    if (viewController != null)
      stack.push(ControllerState(viewController, viewController.stateMap))

  def pop(): Option[SFXViewController] = {
    if (stack.nonEmpty) {
      val controllerState = stack.pop()
      Some(updateContent(controllerState))
    }
    None
  }

  private def updateContent[T <: SFXViewController](controllerState: ControllerState): T = {
    val viewController: SFXViewController = controllerState.controller
    contentManager.updatePaneContent(viewController, pushToStack = false)
    viewController.updateFromStateMap(controllerState.stateMap)

    viewController.asInstanceOf[T]
  }

  def popUntil[T <: SFXViewController](): Option[T] = {
    while (stack.nonEmpty) {
      val result = stack.pop()
      result.controller match {
        case controller: T => return Some(updateContent[T](result))
        case _             =>
      }
    }
    None
  }

  def size: Int = stack.size

}

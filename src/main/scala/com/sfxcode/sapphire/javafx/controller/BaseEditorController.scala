package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.javafx.controller.ViewController
import com.sfxcode.sapphire.javafx.value.{ FXBean, FXBeanAdapter, KeyBindings }
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.Pane

import scala.reflect.ClassTag

abstract class BaseEditorController extends ViewController {

  type R <: AnyRef

  def ct: ClassTag[R]

  var editableBean: Option[FXBean[R]] = None

  @FXML
  var formPane: Pane = _

  lazy val formAdapter: FXBeanAdapter[R] = FXBeanAdapter[R](viewController = this, parent = formPane.asInstanceOf[Node])

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    val bindings = KeyBindings()
    updateBindings(bindings)
    formAdapter.addBindings(bindings)
  }

  def updateBindings(bindings: KeyBindings): Unit

  def updateBean(bean: FXBean[R]): Unit = {
    val value = bean.asInstanceOf[FXBean[R]]
    editableBean = Some(value)
    formAdapter.set(value)
  }

  def actionSave(event: ActionEvent): Unit =
    editableBean.foreach(b => save(b.bean))

  def actionRevert(event: ActionEvent): Unit =
    editableBean.foreach(fxBean => fxBean.revert())

  def save(beanValue: R): Unit

}

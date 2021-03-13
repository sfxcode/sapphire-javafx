package com.sfxcode.sapphire.javafx.controller

import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.sfxcode.sapphire.javafx.value.{SFXBean, SFXBeanAdapter, SFXKeyBindings}
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.Pane

import scala.reflect.ClassTag

abstract class SFXBaseEditorController extends SFXViewController {

  type R <: AnyRef

  def ct: ClassTag[R]

  var editableBean: Option[SFXBean[R]] = None

  @FXML
  var formPane: Pane = _

  lazy val formAdapter: SFXBeanAdapter[R] =
    SFXBeanAdapter[R](viewController = this, parent = formPane.asInstanceOf[Node])

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    val bindings = SFXKeyBindings()
    updateBindings(bindings)
    formAdapter.addBindings(bindings)
  }

  def updateBindings(bindings: SFXKeyBindings): Unit

  def updateBean(bean: SFXBean[R]): Unit = {
    val value = bean.asInstanceOf[SFXBean[R]]
    editableBean = Some(value)
    formAdapter.set(value)
  }

  def actionSave(event: ActionEvent): Unit =
    editableBean.foreach(b => save(b.bean))

  def actionRevert(event: ActionEvent): Unit =
    editableBean.foreach(fxBean => fxBean.revert())

  def save(beanValue: R): Unit

}

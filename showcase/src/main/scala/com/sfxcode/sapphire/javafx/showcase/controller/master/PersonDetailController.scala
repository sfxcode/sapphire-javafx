package com.sfxcode.sapphire.javafx.showcase.controller.master

import com.sfxcode.sapphire.javafx.value.SFXKeyBindings
import com.sfxcode.sapphire.javafx.controller.{ SFXDetailController, SFXMasterController }
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.Person

import scala.reflect._

class PersonDetailController extends SFXDetailController with BaseController {

  type R = Person

  def ct: ClassTag[Person] = classTag[R]

  override def navigateToMasterController(masterController: SFXMasterController): Unit =
    updateShowcaseContent(masterController)

  def updateBindings(bindings: SFXKeyBindings): Unit =
    formAdapter.addBindings(SFXKeyBindings.forClass[Person]())

  override def save(beanValue: Person): Unit = {}
}

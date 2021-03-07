package com.sfxcode.sapphire.javafx.showcase.controller.master

import com.sfxcode.sapphire.javafx.value.KeyBindings
import com.sfxcode.sapphire.javafx.controller.{ BaseDetailController, BaseMasterController }
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.Person

import scala.reflect._

class PersonDetailController extends BaseDetailController with BaseController {

  type R = Person

  def ct: ClassTag[Person] = classTag[R]

  override def navigateToMasterController(masterController: BaseMasterController): Unit =
    updateShowcaseContent(masterController)

  def updateBindings(bindings: KeyBindings): Unit =
    formAdapter.addBindings(KeyBindings.forClass[Person]())

  override def save(beanValue: Person): Unit = {}
}

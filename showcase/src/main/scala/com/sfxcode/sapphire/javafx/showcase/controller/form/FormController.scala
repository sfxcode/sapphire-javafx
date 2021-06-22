package com.sfxcode.sapphire.javafx.showcase.controller.form

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.Pane

import com.sfxcode.sapphire.javafx.value.{ SFXBean, SFXBeanAdapter, SFXKeyBindings }
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.{ Person, PersonDatabase }

import scala.util.Random

class FormController extends BaseController {
  @FXML
  var formPane: Pane = _

  lazy val formAdapter: SFXBeanAdapter[Person] = SFXBeanAdapter[Person](this, formPane.asInstanceOf[Node])
  lazy val adapter: SFXBeanAdapter[Person] = SFXBeanAdapter[Person](this)

  val random = new Random()

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    val bindings = SFXKeyBindings()
    bindings.add("person", "Person ${_self.name()} with age of ${_self.age()} is active: ${_self.isActive()}")
    adapter.addBindings(bindings)

    val bindingList = List("name", "age", "address", "isActive")
    val formBindings = SFXKeyBindings(bindingList, "form1_")
    formBindings.add(bindingList, "form2_")
    formAdapter.addBindings(formBindings)
    formAdapter.addConverter("form1_age", "IntegerStringConverter")
    formAdapter.addConverter("form2_age", "IntegerStringConverter")
    formAdapter.addConverter("form2_isActive", "BooleanStringConverter")

  }

  override def didGainVisibility() {
    super.didGainVisibility()
    setRandomPerson()
  }

  def actionChangePerson(event: ActionEvent): Unit =
    setRandomPerson()

  def setRandomPerson(): Unit = {
    val person: SFXBean[Person] = PersonDatabase.testPerson(random.nextInt(100))
    formAdapter.set(person)
    adapter.set(person)
  }

}

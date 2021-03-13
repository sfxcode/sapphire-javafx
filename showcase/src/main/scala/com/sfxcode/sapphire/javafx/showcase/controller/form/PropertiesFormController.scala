package com.sfxcode.sapphire.javafx.showcase.controller.form

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.layout.AnchorPane

import com.sfxcode.sapphire.javafx.value.{SFXBean, SFXBeanAdapter, SFXKeyBindings}
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.{Person, PersonDatabase}
import com.sfxcode.sapphire.javafx.property.SFXBeanItems
import org.controlsfx.control.PropertySheet

import scala.util.Random

class PropertiesFormController extends BaseController {

  @FXML
  var propPane: AnchorPane = _

  val random = new Random()

  lazy val adapter = SFXBeanAdapter[Person](this)

  // #BeanItemsInit
  val propertySheet = new PropertySheet()
  val beanItems     = SFXBeanItems[Person]()
  // #BeanItemsInit

  override def didGainVisibilityFirstTime() {
    super.didGainVisibilityFirstTime()

    // #BeanItems
    beanItems.addItem("name", "Name", "Basic", "Name")
    beanItems.addItem("age", "Age", "Basic", "Age", editable = false)
    beanItems.addItem("isActive", "Active", "Extended", "Active")
    beanItems.addItem("registered", "Registered", "Extended", "Registered")
    // #BeanItems

    propPane.getChildren.add(propertySheet)

    val bindings = SFXKeyBindings()
    bindings.add(
      "person",
      "Person ${_self.name()} with age of ${_self.age()} is active: ${_self.isActive()} ${sfx:dateString(_self.registered())}"
    )
    adapter.addBindings(bindings)

    setRandomPerson()

  }

  override def didGainVisibility() {
    super.didGainVisibility()
    setRandomPerson()
  }

  def actionChangePerson(event: ActionEvent): Unit =
    setRandomPerson()

  // #BeanItemsUpdate
  def setRandomPerson(): Unit = {
    val person: SFXBean[Person] = PersonDatabase.testPerson(random.nextInt(100))
    adapter.set(person)

    beanItems.updateBean(person)
    propertySheet.getItems.setAll(beanItems.getItems)
  }
  // #BeanItemsUpdate

}

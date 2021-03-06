package com.sfxcode.sapphire.javafx.demo.tutorial.controller.page

import com.sfxcode.sapphire.javafx.demo.tutorial.controller.base.AbstractViewController
import com.sfxcode.sapphire.javafx.demo.tutorial.model.{Person, PersonFactory}
import com.sfxcode.sapphire.javafx.fxml.FxmlLocation
import com.sfxcode.sapphire.javafx.value.{BeanConversions, FXBean, FXBeanAdapter, KeyBindings}
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.layout.VBox

@FxmlLocation(path = "/fxml/custom/path/Person.fxml")
class PersonPageController extends AbstractViewController with BeanConversions {

  // second parameter parent Node is optional,
  // but sometimes needed for the correct NodeLocator lookup
  lazy val adapter: FXBeanAdapter[Person] = FXBeanAdapter[Person](this, personBox)
  @FXML
  var tableView: TableView[FXBean[Person]] = _
  // #adapter_create
  @FXML
  var personBox: VBox = _

  // #adapter_create

  // #bindings
  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()

    // #bindingList #labels
    val bindings = KeyBindings("id", "name", "age", "test")
    // Expression Binding Example
    bindings.add("person", "${sf:i18n('personText', _self.name(), _self.age())}")

    adapter.addBindings(bindings)
    // #bindingList #labels

    // #addConverter  #labels
    adapter.addIntConverter("age")
    // #addConverter  #labels

    tableView.setItems(items)                                                                                // #labels
    tableView.getSelectionModel.selectedItemProperty.addListener((_, _, newValue) => selectPerson(newValue)) // #labels
    personBox.visibleProperty().bind(adapter.hasBeanProperty)
  }

  def items: ObservableList[FXBean[Person]] = PersonFactory.personList

  // #bindings

  // #adapter_use
  def selectPerson(person: FXBean[Person]): Unit = {
    adapter.set(person)
    statusBarController.statusLabel.setText("%s selected".format(person.getValue("name"))) // #labels
  }

  def actionRevert(event: ActionEvent): Unit =
    adapter.revert()

  // #adapter_use

}
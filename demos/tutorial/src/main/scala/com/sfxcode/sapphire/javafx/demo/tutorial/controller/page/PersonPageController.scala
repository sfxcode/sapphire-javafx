package com.sfxcode.sapphire.javafx.demo.tutorial.controller.page

import com.sfxcode.sapphire.javafx.demo.tutorial.controller.base.AbstractViewController
import com.sfxcode.sapphire.javafx.demo.tutorial.model.{ Person, PersonFactory }
import com.sfxcode.sapphire.javafx.fxml.FxmlLocation
import com.sfxcode.sapphire.javafx.value.{ SFXBean, SFXBeanAdapter, SFXBeanConversions, SFXKeyBindings }
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.layout.VBox

@FxmlLocation(path = "/fxml/custom/path/Person.fxml")
class PersonPageController extends AbstractViewController with SFXBeanConversions {

  // second parameter parent Node is optional,
  // but sometimes needed for the correct NodeLocator lookup
  lazy val adapter: SFXBeanAdapter[Person] = SFXBeanAdapter[Person](this, personBox)
  @FXML
  var tableView: TableView[SFXBean[Person]] = _
  // #adapter_create
  @FXML
  var personBox: VBox = _

  // #adapter_create

  // #bindings
  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()

    // #bindingList
    val bindings = SFXKeyBindings("id", "name", "age", "test")
    // Expression Binding Example
    bindings.add("person", "${sfx:i18n('personText', _self.name(), _self.age())}")

    adapter.addBindings(bindings)
    // #bindingList

    // #addConverter
    adapter.addIntConverter("age")
    // #addConverter

    tableView.setItems(items) // #labels
    tableView.getSelectionModel.selectedItemProperty.addListener((_, _, newValue) => selectPerson(newValue)) // #labels
    personBox.visibleProperty().bind(adapter.hasBeanProperty)
  }

  def items: ObservableList[SFXBean[Person]] = PersonFactory.personList

  // #bindings

  // #adapter_use
  def selectPerson(person: SFXBean[Person]): Unit = {
    adapter.set(person)
    statusBarController.statusLabel.setText("%s selected".format(person.getValue("name"))) // #labels
  }

  def actionRevert(event: ActionEvent): Unit =
    adapter.revert()

  // #adapter_use

}

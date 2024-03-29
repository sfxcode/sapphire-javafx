package com.sfxcode.sapphire.javafx.showcase.controller.control

import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.{ Person, PersonDatabase }
import com.sfxcode.sapphire.javafx.value.{ SFXBean, SFXBeanConversions }
import javafx.fxml.FXML
import javafx.scene.control.TableView

import scala.util.Random

class TableValueController extends BaseController with SFXBeanConversions {
  val random = new Random()
  val RandomRange = 10

  @FXML
  var tableView: TableView[SFXBean[Person]] = _

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    tableView.setItems(PersonDatabase.smallPersonTable)
    tableView.getSelectionModel.selectedItemProperty.addListener((_, _, newValue) => selectPerson(newValue))
  }

  def selectPerson(person: SFXBean[Person]): Unit =
    logger.info("%s selected".format(person.getValue("name")))

  def testString: String = "Test " + (random.nextInt(RandomRange) + 1)

}

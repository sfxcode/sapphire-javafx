package com.sfxcode.sapphire.javafx.showcase.controller.control

import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.{ Person, PersonDatabase }
import com.sfxcode.sapphire.javafx.value.{ SFXBean, SFXBeanConversions }
import javafx.fxml.FXML
import javafx.scene.control.{ TreeItem, TreeTableView }

class TreeTableValueController extends BaseController with SFXBeanConversions {

  @FXML
  var tableView: TreeTableView[SFXBean[Person]] = _

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    val rootNode = new TreeItem[SFXBean[Person]](PersonDatabase.smallPersonTable.head)
    rootNode.setExpanded(true)
    PersonDatabase.smallPersonTable.tail.foreach(person =>
      rootNode.getChildren.add(new TreeItem[SFXBean[Person]](person)))
    tableView.setRoot(rootNode)
  }

}

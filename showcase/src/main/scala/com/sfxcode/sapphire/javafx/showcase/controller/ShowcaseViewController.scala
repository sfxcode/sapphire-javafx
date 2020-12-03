package com.sfxcode.sapphire.javafx.showcase.controller

import com.sfxcode.sapphire.javafx.fxml.FxmlLocation
import com.sfxcode.sapphire.javafx.showcase.bean.SimpleBeanController
import com.sfxcode.sapphire.javafx.showcase.controller.control.{
  TableCellController,
  TableValueController,
  TreeTableValueController
}
import com.sfxcode.sapphire.javafx.showcase.controller.font.IconFontController
import com.sfxcode.sapphire.javafx.showcase.controller.form.{
  DualListFormController,
  ExtensionFormController,
  FormController,
  ListFormController,
  PropertiesFormController
}
import com.sfxcode.sapphire.javafx.showcase.controller.master.PersonMasterController
import com.sfxcode.sapphire.javafx.showcase.controller.table.{
  FriendTableController,
  PersonTableController,
  SimplePersonTableController
}
import com.typesafe.scalalogging.LazyLogging

@FxmlLocation(path = "/showcase/ShowcaseView.fxml")
class ShowcaseViewController extends ShowcaseController with LazyLogging {

  private val welcomeItem = ShowcaseItem("Welcome", "Welcome", () => getController[WelcomeController]())

  private val beanBindingsCellItem =
    ShowcaseItem("FXBean", "Bindings", () => getController[SimpleBeanController]())

  private val controlTableValueItem =
    ShowcaseItem("Control", "Table Value Factory", () => getController[TableValueController]())
  private val controlTableCellItem =
    ShowcaseItem("Control", "Table Cell Factory", () => getController[TableCellController]())
  private val controlTreeTableValueItem =
    ShowcaseItem("Control", "TreeTable", () => getController[TreeTableValueController]())

  private val formItem         = ShowcaseItem("Form", "Basic Form", () => getController[FormController]())
  private val listFormItem     = ShowcaseItem("Form", "List Form", () => getController[ListFormController]())
  private val dualListFormItem = ShowcaseItem("Form", "Dual List Form", () => getController[DualListFormController]())
  private val propertiesFormItem =
    ShowcaseItem("Form", "Properties Form", () => getController[PropertiesFormController]())
  private val controlsfxFormItem = ShowcaseItem("Form", "ControlsFX", () => getController[ExtensionFormController]())
  private val controllerMasterItem =
    ShowcaseItem("Controller", "Master / Detail", () => getController[PersonMasterController]())
  private val fontIconItem      = ShowcaseItem("Font", "Icons", () => getController[IconFontController]())
  private val tableBaseItem     = ShowcaseItem("Table", "Base", () => getController[SimplePersonTableController]())
  private val dataTableBaseItem = ShowcaseItem("DataTable", "Base", () => getController[FriendTableController]())
  private val dataTableExtendedItem =
    ShowcaseItem("DataTable", "Extended", () => getController[PersonTableController]())

  private val items = List(
    beanBindingsCellItem,
    controlTableValueItem,
    controlTableCellItem,
    controlTreeTableValueItem,
    formItem,
    listFormItem,
    dualListFormItem,
    propertiesFormItem,
    controlsfxFormItem,
    fontIconItem,
    controllerMasterItem,
    tableBaseItem,
    dataTableBaseItem,
    dataTableExtendedItem
  )

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    updateShowcaseItems(items)
    changeShowcaseItem(welcomeItem)
  }
}

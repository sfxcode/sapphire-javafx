package com.sfxcode.sapphire.javafx.showcase.controller.form

import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.Pane

import com.sfxcode.sapphire.javafx.value.{ FXBean, FXBeanAdapter, KeyBindings }
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.BookRating

import scala.util.Random

class ExtensionFormController extends BaseController {
  @FXML
  var formPane: Pane = _

  lazy val formAdapter: FXBeanAdapter[BookRating] = FXBeanAdapter[BookRating](this, formPane.asInstanceOf[Node])

  val random = new Random()

  override def didGainVisibilityFirstTime() {
    super.didGainVisibilityFirstTime()

    val bindingList = List("name", "rating", "pages")
    val formBindings = KeyBindings(bindingList, "form1_")
    formBindings.add(bindingList, "form2_")
    formAdapter.addBindings(formBindings)
    formAdapter.addConverter("form2_rating", "DoubleStringConverter")
    formAdapter.addConverter("form2_pages", "DoubleStringConverter")

  }

  override def didGainVisibility() {
    super.didGainVisibility()
    formAdapter.set(FXBean[BookRating](BookRating(3, "Book", 3, 250)))
  }

}

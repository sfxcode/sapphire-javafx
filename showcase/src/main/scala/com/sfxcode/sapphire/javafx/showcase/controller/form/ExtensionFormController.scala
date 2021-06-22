package com.sfxcode.sapphire.javafx.showcase.controller.form

import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.Pane

import com.sfxcode.sapphire.javafx.value.{ SFXBean, SFXBeanAdapter, SFXKeyBindings }
import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.BookRating

import scala.util.Random

class ExtensionFormController extends BaseController {
  @FXML
  var formPane: Pane = _

  lazy val formAdapter: SFXBeanAdapter[BookRating] = SFXBeanAdapter[BookRating](this, formPane.asInstanceOf[Node])

  val random = new Random()

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()

    val bindingList = List("name", "rating", "pages")
    val formBindings = SFXKeyBindings(bindingList, "form1_")
    formBindings.add(bindingList, "form2_")
    formAdapter.addBindings(formBindings)
    formAdapter.addConverter("form2_rating", "DoubleStringConverter")
    formAdapter.addConverter("form2_pages", "DoubleStringConverter")

  }

  override def didGainVisibility() {
    super.didGainVisibility()
    formAdapter.set(SFXBean[BookRating](BookRating(3, "Book", 3, 250)))
  }

}

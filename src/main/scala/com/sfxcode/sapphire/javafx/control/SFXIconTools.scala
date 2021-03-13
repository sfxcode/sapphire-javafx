package com.sfxcode.sapphire.javafx.control

import org.kordamp.ikonli.javafx.FontIcon
import javafx.scene.control.Button

object SFXIconTools {

  def decoratedFontIconButton(code: String): Button = {
    val result = new Button()
    result.setGraphic(new FontIcon(code))
    result
  }
}

package com.sfxcode.sapphire.javafx.test

import com.sfxcode.sapphire.javafx.controller.SFXViewController
import javafx.scene.layout.HBox

class TestViewController extends SFXViewController {

  var name: String = ""

  rootPane = new HBox()

  override def didGainVisibilityFirstTime(): Unit =
    super.willGainVisibility()

  override def didGainVisibility(): Unit =
    super.didGainVisibility()

}

package com.sfxcode.sapphire.javafx.assets

object SFXResourceLoader {

  def loadAsExternalForm(path: String): String =
    getClass.getResource(path).toExternalForm
}

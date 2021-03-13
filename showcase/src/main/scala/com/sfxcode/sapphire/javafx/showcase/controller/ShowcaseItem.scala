package com.sfxcode.sapphire.javafx.showcase.controller

import com.sfxcode.sapphire.javafx.controller.SFXViewController

import java.net.URL

case class ShowcaseItem(group: String, name: String, callback: () => SFXViewController) {
  lazy val controller: SFXViewController = callback()

  def fxmlPath: URL = controller.location.get

  def sourcePath: URL = {
    val path = "/code/%s.txt".format(controller.getClass.getSimpleName)
    getClass.getResource(path)
  }

  def documentationPath: URL = {
    val path = "/markdown/%s.md".format(controller.getClass.getSimpleName)
    getClass.getResource(path)
  }

}

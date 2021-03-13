package com.sfxcode.sapphire.javafx.scene

import javafx.beans.property.Property
import javafx.scene.Node

trait SFXNodePropertyResolving {

  def resolve(node: Node): Option[Property[_]]

}

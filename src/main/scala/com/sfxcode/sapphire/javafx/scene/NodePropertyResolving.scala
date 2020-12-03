package com.sfxcode.sapphire.javafx.scene

import javafx.beans.property.Property
import javafx.scene.Node

trait NodePropertyResolving {

  def resolve(node: Node): Option[Property[_]]

}

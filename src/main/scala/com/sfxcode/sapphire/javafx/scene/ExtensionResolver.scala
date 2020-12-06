package com.sfxcode.sapphire.javafx.scene

import com.sfxcode.sapphire.javafx.application.ApplicationEnvironment
import javafx.scene.Node
import org.controlsfx.control.Rating
import javafx.beans.property.Property

// #NodePropertyResolving
class ExtensionResolver extends NodePropertyResolving {

  def resolve(node: Node): Option[Property[_]] =
    node match {
      case rating: Rating => Some(rating.ratingProperty())
      case _              => None
    }
}
// #NodePropertyResolving

object ExtensionResolver {

  def apply(): ExtensionResolver = new ExtensionResolver()

  def add(): Unit =
    ApplicationEnvironment.nodePropertyResolver.addResolver(ExtensionResolver())
}

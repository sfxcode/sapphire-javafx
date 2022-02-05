package com.sfxcode.sapphire.javafx.scene

import com.sfxcode.sapphire.javafx.application.SFXApplicationEnvironment
import javafx.scene.Node
import org.controlsfx.control.Rating
import javafx.beans.property.Property

// #NodePropertyResolving
class SFXExtensionResolver extends SFXNodePropertyResolving {

  def resolve(node: Node): Option[Property[_]] =
    node match {
      case rating: Rating => Some(rating.ratingProperty())
      case _ => None
    }
}
// #NodePropertyResolving

object SFXExtensionResolver {

  def apply(): SFXExtensionResolver = new SFXExtensionResolver()

  def add(): Unit =
    SFXApplicationEnvironment.nodePropertyResolver.addResolver(SFXExtensionResolver())
}

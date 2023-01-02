package com.sfxcode.sapphire.javafx.scene

import javafx.beans.property.Property
import javafx.scene.Node

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class SFXNodePropertyResolver {
  val resolverBuffer = new ArrayBuffer[SFXNodePropertyResolving]()
  addResolver(SFXDefaultResolver())

  def addResolver(resolver: SFXNodePropertyResolving): Unit =
    resolverBuffer.+=(resolver)

  def resolve(node: Node): Option[Property[_]] = {
    var maybeProperty: Option[Property[_]] = None
    breakable {
      resolverBuffer.foreach {
        r =>
          val result = r.resolve(node)
          if (result.isDefined) {
            maybeProperty = result
            break()
          }
      }
    }
    maybeProperty
  }

}

object SFXNodePropertyResolver {
  def apply(): SFXNodePropertyResolver =
    new SFXNodePropertyResolver
}

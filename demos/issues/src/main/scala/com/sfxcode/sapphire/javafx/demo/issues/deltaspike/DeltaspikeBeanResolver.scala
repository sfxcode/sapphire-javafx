package com.sfxcode.sapphire.javafx.demo.issues.deltaspike

import org.apache.deltaspike.core.api.provider.BeanProvider

import scala.reflect.ClassTag

trait DeltaspikeBeanResolver {

  def getBean[T <: AnyRef](optional: Boolean = false)(implicit ct: ClassTag[T]): T = {
    val clazz = ct.runtimeClass
    BeanProvider.getContextualReference(clazz, optional).asInstanceOf[T]
  }

}

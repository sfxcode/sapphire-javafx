package com.sfxcode.sapphire.javafx.value

import com.sfxcode.sapphire.javafx.SFXCollectionExtensions._
import javafx.collections.ObservableList

import scala.jdk.CollectionConverters._
import scala.language.implicitConversions

trait SFXBeanConversions {

  implicit def beanToFXBean[T <: AnyRef](bean: T): SFXBean[T] = SFXBean(bean)

  implicit def fxBeanToBean[T <: AnyRef](fxBean: SFXBean[T]): T = fxBean.bean

  implicit def beanToFXBeanOption[T <: AnyRef](bean: T): Option[SFXBean[T]] =
    if (bean != null)
      Some(SFXBean(bean))
    else
      None

  implicit def optionBeanToFXBeanOption[T <: AnyRef](bean: Option[T]): Option[SFXBean[T]] =
    if (bean.isDefined)
      Some(SFXBean(bean.get))
    else
      None

  implicit def beansToObservableList[T <: AnyRef](iterable: Iterable[T]): ObservableList[SFXBean[T]] =
    iterable.map(
      item => SFXBean[T](item))

  implicit def observableListToBeans[T <: AnyRef](list: ObservableList[SFXBean[T]]): Iterable[T] =
    list.asScala.map(
      item => item.bean)

}

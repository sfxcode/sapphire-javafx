package com.sfxcode.sapphire.javafx.control

import java.text.{DecimalFormat, SimpleDateFormat}
import com.sfxcode.sapphire.javafx.value.SFXBean
import com.sfxcode.sapphire.data.reflect.ReflectionTools
import javafx.beans.property._
import javafx.beans.value.ObservableValue

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import scala.beans.BeanProperty

trait SFXFXValueFactory[S, T] {

  lazy val numberFormatter: DecimalFormat       = new DecimalFormat(format)
  lazy val dateFormatter: SimpleDateFormat      = new SimpleDateFormat(format)
  lazy val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(format)

  @BeanProperty
  var property = ""

  @BeanProperty
  var format = ""

  protected def resolveValue(value: S): ObservableValue[T] =
    value match {
      case bean: SFXBean[_] =>
        var p = bean.getProperty(property)
        if (format.length > 0)
          p match {
            case intProperty: IntegerProperty => p = new SimpleStringProperty(numberFormatter.format(intProperty.get))
            case longProperty: LongProperty   => p = new SimpleStringProperty(numberFormatter.format(longProperty.get))
            case floatProperty: FloatProperty => p = new SimpleStringProperty(numberFormatter.format(floatProperty.get))
            case doubleProperty: DoubleProperty =>
              p = new SimpleStringProperty(numberFormatter.format(doubleProperty.get))
            case _: StringProperty if bean.get(property).isInstanceOf[Date] =>
              p = new SimpleStringProperty(dateFormatter.format(bean.get(property)))
            case _: StringProperty if bean.get(property).isInstanceOf[LocalDate] =>
              p = new SimpleStringProperty(dateTimeFormatter.format(bean.get(property).asInstanceOf[LocalDate]))
            case _ =>
          }
        p.asInstanceOf[ObservableValue[T]]
      case _ =>
        val reflectedValue = ReflectionTools.getFieldValue(value, property)
        reflectedValue match {
          case ov: ObservableValue[T] => ov
          case _                      => null
        }
    }

}

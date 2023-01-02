package com.sfxcode.sapphire.javafx.application

import com.sfxcode.sapphire.javafx.Configuration

import java.util.Date
import com.sfxcode.sapphire.data.el.FunctionHelper
import com.sfxcode.sapphire.javafx.SFXResourceBundleHolder
import com.sfxcode.sapphire.javafx.value.Converter.defaultDateConverter

import scala.annotation.varargs

object SFXDefaultFunctions extends Configuration {
  val SapphireFunctionPrefix = "sfx"

  private lazy val recourceBundleHolder = SFXResourceBundleHolder(SFXApplicationEnvironment.resourceBundle)

  def addDefaultFunctions(helper: FunctionHelper): FunctionHelper = {
    val clazz: Class[_] = Class.forName("com.sfxcode.sapphire.javafx.application.SFXDefaultFunctions")
    helper.addFunction(SapphireFunctionPrefix, "frameworkName", clazz, "frameworkName")
    helper.addFunction(SapphireFunctionPrefix, "frameworkVersion", clazz, "frameworkVersion")
    helper.addFunction(SapphireFunctionPrefix, "dateString", clazz, "dateString", classOf[AnyRef])
    helper.addFunction(SapphireFunctionPrefix, "now", clazz, "now")
    helper.addFunction(SapphireFunctionPrefix, "nowAsString", clazz, "nowAsString")
    helper.addFunction(SapphireFunctionPrefix, "boolString", clazz, "boolString", classOf[Boolean], classOf[String], classOf[String])
    helper.addFunction(SapphireFunctionPrefix, "configString", clazz, "configString", classOf[String])
    helper.addFunction(SapphireFunctionPrefix, "i18n", clazz, "i18n", classOf[String], classOf[Array[Any]])
    helper.addFunction(SapphireFunctionPrefix, "format", classOf[java.lang.String], "format", classOf[String], classOf[Array[Any]])
    helper
  }

  def frameworkName(): String = com.sfxcode.sapphire.javafx.BuildInfo.name

  def frameworkVersion(): String = com.sfxcode.sapphire.javafx.BuildInfo.version

  @varargs
  def i18n(key: String, params: Any*): String = recourceBundleHolder.message(key, params: _*)

  def boolString(value: Boolean, trueValue: String, falseValue: String): String =
    if (value) {
      trueValue
    } else {
      falseValue
    }

  def now: Date = new Date

  def nowAsString: String = dateString(new java.util.Date)

  def dateString(date: AnyRef): String = {
    val s = date match {
      case d: java.util.Date => defaultDateConverter.toString(d)
      case c: java.util.Calendar => defaultDateConverter.toString(c.getTime)
      case c: javax.xml.datatype.XMLGregorianCalendar =>
        defaultDateConverter.toString(c.toGregorianCalendar.getTime)
      case _ => "unknown date format"
    }
    s
  }

  def configString(path: String): String = configStringValue(path)

}

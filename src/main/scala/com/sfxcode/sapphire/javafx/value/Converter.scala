package com.sfxcode.sapphire.javafx.value

import com.sfxcode.sapphire.javafx.Configuration
import javafx.util.converter.{ DateStringConverter, DateTimeStringConverter }

object Converter extends Configuration {

  val DefaultDateConverterPattern: String = configStringValue("sapphire.defaultDateConverterPattern")
  val DefaultDateTimeConverterPattern: String = configStringValue(
    "com.sfxcode.sapphire.data.defaultDateTimeConverterPattern")

  var defaultDateConverter = new DateStringConverter(DefaultDateConverterPattern)
  var defaultDateTimeConverter = new DateTimeStringConverter(DefaultDateTimeConverterPattern)
}

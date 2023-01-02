package com.sfxcode.sapphire.javafx.application

import java.text.SimpleDateFormat

import com.sfxcode.sapphire.data.el.Expressions
import org.specs2.mutable.Specification

class DefaultFunctionsSpec extends Specification {

  "Expressions" should {

    "evaluate default functions" in {
      val functionHelper = Expressions.functionHelper
      SFXDefaultFunctions.addDefaultFunctions(functionHelper)

      (Expressions.getValue("${sfx:frameworkName()}").get must be).equalTo("sapphire-javafx")

      val df = new SimpleDateFormat("yyyy-MM-dd")
      val date = df.parse("2015-01-01")

      Expressions.register("testDate", date)

      (Expressions.getValue("${sfx:dateString(testDate)}").get must be).equalTo("2015-01-01")

      val nowString = Expressions.getValue("${sfx:nowAsString()}").get.toString

      nowString must not beEmpty

      Expressions.register("testBoolean", true)

      (Expressions.getValue("${sfx:boolString(testBoolean,'Y', 'N')}").get must be).equalTo("Y")

      // #coreFunction
      Expressions.register("testBoolean", false)
      (Expressions.getValue("${sfx:boolString(testBoolean,'Y', 'N')}").get must be).equalTo("N")
      // #coreFunction

      (Expressions.getValue("${sfx:configString('test.string')}").get must be).equalTo("Hello World")

    }

  }
}

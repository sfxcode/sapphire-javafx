package com.sfxcode.sapphire.javafx.base

import com.sfxcode.sapphire.javafx.Configuration
import org.specs2.mutable.Specification

class ConfigValuesSpec extends Specification with Configuration {

  "ConfigValues" should {

    "evaluate values" in {
      (configIntValue("test.int") must be).equalTo(42)
      (configIntValue("test.int.notExists") must be).equalTo(0)
      (configIntValue("test.int.notExists", 3) must be).equalTo(3)
    }

    "evaluate values" in {
      (configIntValues("test.list.int") must be).equalTo(List(1, 2, 3))
    }

  }
}

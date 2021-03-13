package com.sfxcode.sapphire.javafx.value

import com.sfxcode.sapphire.javafx.test.TestBean
import org.specs2.mutable.Specification

class SFXKeyBindingsSpec extends Specification {

  "KeyBindings" should {

    "should have default constructor" in {
      val bindings = SFXKeyBindings("name", "age")
      bindings.keys must haveSize(2)
      bindings("name") must be equalTo "name"

    }

    "should be created by class tag" in {
      val bindings = SFXKeyBindings.forClass[TestBean]("test_")
      bindings.keys must haveSize(5)
      bindings("test_name") must be equalTo "name"
    }
  }
}

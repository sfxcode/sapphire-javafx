package com.sfxcode.sapphire.javafx.value

import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.sfxcode.sapphire.javafx.test.{ Person, PersonDatabase, TestViewController }
import org.specs2.mutable.Specification

class SFXBeanAdapterSpec extends Specification {
  val adapter: SFXBeanAdapter[Person] = SFXBeanAdapter[Person](new TestViewController)

  sequential

  "FXBeanAdapter" should {

    "be initialized with controller" in {
      adapter.viewController must haveSuperclass[SFXViewController]
    }

    "be updated with FXBean" in {
      val testPerson = PersonDatabase.testPerson(0)
      adapter.hasBeanProperty.get must beFalse
      adapter.set(testPerson)
      adapter.hasBeanProperty.get must beTrue
    }
  }

}

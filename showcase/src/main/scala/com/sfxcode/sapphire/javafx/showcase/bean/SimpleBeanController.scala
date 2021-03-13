package com.sfxcode.sapphire.javafx.showcase.bean

import com.sfxcode.sapphire.javafx.showcase.controller.BaseController
import com.sfxcode.sapphire.javafx.showcase.model.{Person, PersonDatabase}
import com.sfxcode.sapphire.javafx.value.{SFXBean, SFXBeanAdapter, SFXBeanConversions, SFXKeyBindings}

import scala.util.Random

class SimpleBeanController extends BaseController with SFXBeanConversions {
  val random      = new Random()
  val RandomRange = 100

  lazy val adapter: SFXBeanAdapter[Person] = SFXBeanAdapter[Person](this)

  override def didGainVisibilityFirstTime(): Unit = {
    super.didGainVisibilityFirstTime()
    val bindings = SFXKeyBindings()
    bindings.add(List("name", "age", "address", "isActive"))
    bindings.add("person", "Person ${_self.name()} with age of ${_self.age()} is active: ${_self.isActive()}")
    adapter.addBindings(bindings)
    adapter.addConverter("age", "IntegerStringConverter")
    adapter.addConverter("isActive", "BooleanStringConverter")

  }

  override def didGainVisibility() {
    super.didGainVisibility()
    setRandomPerson()
  }

  private def setRandomPerson(): Unit = {
    val person: SFXBean[Person] = PersonDatabase.testPerson(random.nextInt(RandomRange))
    adapter.set(person)
  }

}

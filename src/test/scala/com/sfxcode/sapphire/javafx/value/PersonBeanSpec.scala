package com.sfxcode.sapphire.javafx.value

import com.sfxcode.sapphire.javafx.test.{ Person, PersonDatabase }
import org.specs2.mutable.Specification

class PersonBeanSpec extends Specification {

  sequential

  "FXBean of Person" should {

    "get value of members of case class" in {
      val person: SFXBean[Person] = PersonDatabase.testPerson(0)

      (person.bean.id must be).equalTo(0)
      (person.bean.name must be).equalTo("Cheryl Hoffman")
      (person.bean.age must be).equalTo(25)

      (person.getValue("name") must be).equalTo("Cheryl Hoffman")
      (person.getValue("age") must be).equalTo(25)

      (person("name") must be).equalTo("Cheryl Hoffman")
      (person("age") must be).equalTo(25)
    }

    "get value by expression" in {
      // #expression
      val person: SFXBean[Person] = PersonDatabase.testPerson(0)
      (person.getValue("${_self.name()}") must be).equalTo("Cheryl Hoffman")
      (person.getValue("${_self.age()}") must be).equalTo(25)
      (person.getValue("${_self.age() * 2}") must be).equalTo(50)
      // #expression

    }

    "update value of members of case class" in {
      val person: SFXBean[Person] = PersonDatabase.testPerson(0)

      (person.getValue("age") must be).equalTo(25)

      person.updateValue("age", 26)

      (person.getOldValue("age") must be).equalTo(25)
      (person.getValue("age") must be).equalTo(26)

      person.revert()
      (person.getValue("age") must be).equalTo(25)

      person.bean.age must be equalTo (25)
      person.getBean().age must be equalTo (25)

    }
  }

}

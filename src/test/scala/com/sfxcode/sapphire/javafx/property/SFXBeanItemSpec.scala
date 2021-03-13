package com.sfxcode.sapphire.javafx.property

import java.util

import com.sfxcode.sapphire.javafx.value.SFXBean
import com.sfxcode.sapphire.javafx.test.{Person, PersonDatabase}
import com.typesafe.scalalogging.LazyLogging
import org.specs2.mutable.Specification

class SFXBeanItemSpec extends Specification with LazyLogging {
  sequential

  "BeanItem" should {

    "be created with bean property" in {
      val person: SFXBean[Person] = PersonDatabase.testPerson(1)
      person.bean.name must be equalTo "Bowen Leon"

      val nameItem = BeanItem(person, "name")

      nameItem.setValue("ABC")

      person.bean.name must be equalTo "ABC"

      nameItem.getType.toString must be equalTo "class java.lang.String"

      val ageItem = BeanItem(person, "age")
      ageItem.getType.toString must be equalTo "int"

      val activeItem = BeanItem(person, "isActive")
      activeItem.getType.toString must be equalTo "boolean"

    }

    "be created with map property" in {
      val map = new util.HashMap[String, Any]()
      map.put("name", "name")
      map.put("age", 42)
      map.put("isActive", true)
      val person = SFXBean(map)

      val nameItem = BeanItem(person, "name")

      nameItem.setValue("ABC")

      person.getValue("name") must be equalTo "ABC"

      nameItem.getType.toString must be equalTo "class java.lang.String"

      val ageItem = BeanItem(person, "age")
      ageItem.getType.toString must be equalTo "class java.lang.Integer"

      val activeItem = BeanItem(person, "isActive")
      activeItem.getType.toString must be equalTo "class java.lang.Boolean"

    }

    "be created with class" in {
      val map = new util.HashMap[String, Any]()
      map.put("name", "ABC")
      map.put("age", 42)
      map.put("isActive", true)
      val person   = SFXBean(map)
      val nameItem = BeanItem(SFXBean(Map()), "name", clazz = classOf[String])
      nameItem.getType.toString must be equalTo "class java.lang.String"
      nameItem.bean = person

      nameItem.getValue.toString must be equalTo "ABC"

    }

  }

}

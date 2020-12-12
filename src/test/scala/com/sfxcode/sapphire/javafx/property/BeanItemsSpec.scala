package com.sfxcode.sapphire.javafx.property

import java.util

import com.sfxcode.sapphire.javafx.value.FXBean
import com.sfxcode.sapphire.javafx.test.{Friend, Person, PersonDatabase}
import com.typesafe.scalalogging.LazyLogging
import org.specs2.mutable.Specification
import scala.jdk.CollectionConverters._

class BeanItemsSpec extends Specification with LazyLogging {
  sequential

  "BeanItems" should {

    "add Items" in {

      val friend: FXBean[Friend] = PersonDatabase.testFriend(2)
      val beanItems              = BeanItems(friend)

      beanItems.addItem("id")
      beanItems.addItem("name")

      val items = beanItems.getItems

      items must have size 2

    }

    "add Items from bean" in {
      val friend: FXBean[Friend] = PersonDatabase.testFriend(2)

      val beanItems = BeanItems(friend)
      beanItems.addItems[Friend]
      val items = beanItems.getItems

      items must have size 2
      val nameItem = items.get(0)
      nameItem.getName must be equalTo "name"
      nameItem.getValue must be equalTo "Wendy Strong"

      val idItem = items.get(1)
      idItem.getName must be equalTo "id"
      idItem.getValue.asInstanceOf[Long] must be equalTo 2

    }

    "add Items from map" in {

      val map = new util.HashMap[String, Any]()
      map.put("name", "ABC")
      map.put("age", 42)
      map.put("isActive", true)

      val beanItems = BeanItems(FXBean(map))
      beanItems.addItemsFromMap(map.asScala.toMap)

      val items = beanItems.getItems

      items must have size 3
      val nameItem = beanItems.beanItem("name").get
      nameItem.getType.toString must be equalTo "class java.lang.String"
      nameItem.getValue.toString must be equalTo "ABC"
    }

  }

}

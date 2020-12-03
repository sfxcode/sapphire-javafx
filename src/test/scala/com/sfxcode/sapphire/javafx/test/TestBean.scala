package com.sfxcode.sapphire.javafx.test

import javafx.beans.property.{ Property, SimpleStringProperty }

case class Zip(value: Long = 12345)

case class TestBean(
  name: String = "test",
  age: Int = 42,
  zip: Zip = Zip(),
  description: Option[String] = Some("desc"),
  observable: Property[_] = new SimpleStringProperty("observable")) {
  def doubleAge(): Int = age * 2

  def multiply(first: java.lang.Long, second: java.lang.Long): Long = first * second

}

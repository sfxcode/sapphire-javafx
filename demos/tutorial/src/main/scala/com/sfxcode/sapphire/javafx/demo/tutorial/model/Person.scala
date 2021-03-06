package com.sfxcode.sapphire.javafx.demo.tutorial.model

case class Person(id: Int, name: String, age: Int)

object PersonFactory {
  val personList: List[Person] = List(Person(1, "Tom", 42), Person(2, "Tim", 43), Person(3, "Bob", 44))

  def person: Person = personList.head
}

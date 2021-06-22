package com.sfxcode.sapphire.javafx.test

import com.sfxcode.sapphire.javafx.value.SFXBean
import io.circe.generic.auto._
import io.circe.parser._

import scala.io.Source

case class Person(
  id: Long,
  guid: String,
  isActive: Boolean,
  balance: Double,
  picture: String,
  age: Int,
  name: String,
  gender: String,
  email: String,
  phone: String,
  address: String,
  about: String,
  tags: List[String],
  friends: List[Friend],
  greeting: String,
  favoriteFruit: String)

case class Friend(id: Long, name: String) {
  def getValue(s: String): String = s
}

object PersonDatabase {

  private val jsonString: String = fromJson("/test_data.json")
  val personen: List[Person] = decode[List[Person]](jsonString).getOrElse(List())

  val friends: List[Friend] = personen.head.friends

  def fromJson(name: String): String = {
    val is = getClass.getResourceAsStream(name)
    Source.fromInputStream(is, "UTF-8").getLines().mkString
  }

  def testPerson(id: Int) = SFXBean(personen(id))

  def testFriend(id: Int) = SFXBean(friends(id))

  def testPersonen: List[SFXBean[Person]] = personen.map(item => SFXBean[Person](item))

  def testFriends: List[SFXBean[Friend]] = friends.map(item => SFXBean[Friend](item))
}

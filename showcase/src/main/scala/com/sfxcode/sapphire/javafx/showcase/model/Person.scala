package com.sfxcode.sapphire.javafx.showcase.model

import com.sfxcode.sapphire.javafx.value.SFXBean
import io.circe.generic.auto._
import io.circe.parser._

import java.util.Date
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

case class Person(
  id: Long,
  guid: String,
  isActive: Boolean,
  company: String,
  balance: Double,
  picture: String,
  var age: Int,
  var name: String,
  gender: String,
  email: String,
  phone: String,
  address: String,
  about: String,
  registered: Date,
  tags: List[String],
  friends: List[Friend],
  greeting: String,
  favoriteFruit: String)

case class Friend(id: Long, name: String) {
  def getValue(s: String): String = s
}

object PersonDatabase extends CirceSchema {

  private val jsonString: String = fromJson("/data.json")
  val persons: List[Person] = decode[List[Person]](jsonString).getOrElse(List())

  val smallPersonTable =
    persons.take(10)

  val bigPersonTable = {
    var result = ArrayBuffer[Person]()
    (1 to 25).foreach { i =>
      result.++=(persons)
    }
    result.toList
  }

  val friends = persons(2).friends

  def fromJson(name: String): String = {
    val is = getClass.getResourceAsStream(name)
    Source.fromInputStream(is, "UTF-8").getLines().mkString
  }

  def testPerson(id: Int) = SFXBean(persons(id))

  def testFriend(id: Int) = SFXBean(friends(id))

  def smallPersonList = persons.take(10)

}

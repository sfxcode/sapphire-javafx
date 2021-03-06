package com.sfxcode.sapphire.javafx.test

import java.text.SimpleDateFormat
import java.util.Date

import com.sfxcode.sapphire.javafx.value.SFXBean
import org.json4s.DefaultFormats
import org.json4s.native.Serialization._

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
  registered: Date,
  tags: List[String],
  friends: List[Friend],
  greeting: String,
  favoriteFruit: String)

case class Friend(id: Long, name: String)

object PersonDatabase {

  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
  }

  val personen: List[Person] = read[List[Person]](fromJson("/test_data.json"))

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

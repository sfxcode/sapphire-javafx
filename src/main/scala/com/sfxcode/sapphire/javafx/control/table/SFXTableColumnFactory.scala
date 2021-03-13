package com.sfxcode.sapphire.javafx.control.table

import com.sfxcode.sapphire.data.Configuration
import com.sfxcode.sapphire.javafx.control.{SFXTableCellFactory, SFXTableValueFactory}
import com.sfxcode.sapphire.javafx.value.SFXBean

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.reflect.runtime.{universe => ru}
import javafx.scene.control.TableColumn
import javafx.scene.text.TextAlignment

object SFXTableColumnFactory extends Configuration {
  val rightAlignmentList = List("Date", "Calendar", "Int", "Long", "Double", "Float")

  val PrefWidth: Double = configDoubleValue("sapphire.javafx.TableColumnFactory.prefWidth", 80.0)

  def columnFromFactories[S <: AnyRef, T](
      header: String,
      valueFactory: SFXTableValueFactory[SFXBean[S], T],
      cellFactory: Option[SFXTableCellFactory[SFXBean[S], T]] = None
  ): TableColumn[SFXBean[S], T] = {
    val column = new TableColumn[SFXBean[S], T](header)
    column.setPrefWidth(PrefWidth)
    column.setCellValueFactory(valueFactory)
    if (cellFactory.isDefined)
      column.setCellFactory(cellFactory.get)

    column
  }

  def columnListFromMembers[S <: AnyRef, T](
      members: List[ru.Symbol],
      columnHeaderMap: Map[String, String],
      columnPropertyMap: Map[String, String],
      editable: Boolean = false,
      numberFormat: String = "#,##0",
      decimalFormat: String = "#,##0.00"
  ): (List[String], Map[String, TableColumn[SFXBean[S], T]]) = {
    val buffer  = new ArrayBuffer[String]()
    val map     = mutable.HashMap[String, TableColumn[SFXBean[S], T]]()
    val symbols = members.collect({ case x if x.isTerm => x.asTerm }).filter(t => t.isVal || t.isVar).map(_.asTerm)
    symbols.foreach { symbol =>
      val name = symbol.name.toString.trim
      buffer.+=(name)
      val cellFactory = new SFXTableCellFactory[SFXBean[S], T]()
      val signature   = symbol.typeSignature.toString
      if (editable)
        cellFactory.setConverter(signature.replace("Int", "Integer"))

      if (shouldAlignRight(signature))
        cellFactory.setAlignment(TextAlignment.RIGHT)

      val property     = columnPropertyMap.getOrElse(name, name)
      val valueFactory = new SFXTableValueFactory[SFXBean[S], T]()
      valueFactory.setProperty(property)
      if (editable)
        if (signature.contains("Int") || signature.contains("Long"))
          valueFactory.format = numberFormat
        else if (signature.contains("Double") || signature.contains("Float"))
          valueFactory.format = decimalFormat

      map.put(
        property,
        columnFromFactories[S, T](
          columnHeaderMap.getOrElse(name, propertyToHeader(name)),
          valueFactory,
          Some(cellFactory)
        )
      )

    }
    (buffer.toList, map.toMap)
  }

  private def propertyToHeader(property: String): String = {
    if (property.length == 1)
      return property.toUpperCase
    val result = new mutable.StringBuilder()
    result.append(property.charAt(0).toUpper)
    property.substring(1).toCharArray.foreach { c =>
      if (c.isUpper)
        result.append(" " + c)
      else
        result.append(c)
    }
    result.toString()
  }

  private def shouldAlignRight(signature: String): Boolean = {
    rightAlignmentList.foreach { s =>
      if (signature.contains(s))
        return true
    }
    false
  }

}

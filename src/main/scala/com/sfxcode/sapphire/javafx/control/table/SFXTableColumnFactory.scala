package com.sfxcode.sapphire.javafx.control.table

import com.sfxcode.sapphire.data.Configuration
import com.sfxcode.sapphire.javafx.control.{SFXTableCellFactory, SFXTableValueFactory}
import com.sfxcode.sapphire.javafx.value.SFXBean
import javafx.scene.control.TableColumn
import javafx.scene.text.TextAlignment

import java.lang.reflect.Field
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object SFXTableColumnFactory extends Configuration {
  val rightAlignmentList = List("int", "long", "double", "float")

  val PrefWidth: Double            = configDoubleValue("sapphire.javafx.TableColumnFactory.prefWidth", 80.0)
  val DefaultNumberFormat: String  = configStringValue("sapphire.defaultNumberFormat", "#,##0")
  val DefaultDecimalFormat: String = configStringValue("sapphire.defaultDecimalFormat", "#,##0.00")

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
      fieldMap: Map[String, Field],
      columnHeaderMap: Map[String, String],
      columnPropertyMap: Map[String, String],
      editable: Boolean = false,
      numberFormat: String = DefaultNumberFormat,
      decimalFormat: String = DefaultDecimalFormat
  ): (List[String], Map[String, TableColumn[SFXBean[S], T]]) = {
    val buffer = new ArrayBuffer[String]()
    val map    = mutable.HashMap[String, TableColumn[SFXBean[S], T]]()

    fieldMap.keySet.foreach { name =>
      val field: Field = fieldMap(name)
      val signature    = field.getGenericType.getTypeName.toLowerCase

      buffer.+=(name)
      val cellFactory = new SFXTableCellFactory[SFXBean[S], T]()
      if (editable)
        cellFactory.setConverter(signature.replace("Int", "Integer"))

      if (shouldAlignRight(signature))
        cellFactory.setAlignment(TextAlignment.RIGHT)

      val property     = columnPropertyMap.getOrElse(name, name)
      val valueFactory = new SFXTableValueFactory[SFXBean[S], T]()
      valueFactory.setProperty(property)

      if (signature.contains("int") || signature.contains("long"))
        valueFactory.format = numberFormat
      else if (signature.contains("double") || signature.contains("float"))
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

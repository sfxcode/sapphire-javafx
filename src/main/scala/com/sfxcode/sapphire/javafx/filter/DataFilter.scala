package com.sfxcode.sapphire.javafx.filter

import com.sfxcode.sapphire.data.FieldProperties.defaultDateConverter
import javafx.scene.Node
import javafx.scene.layout.Pane
import com.sfxcode.sapphire.javafx.value.FXBean
import com.sfxcode.sapphire.javafx.filter.FilterType._
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import org.controlsfx.control.textfield.TextFields

import scala.collection.mutable
import javafx.beans.property.ObjectProperty
import javafx.collections.{FXCollections, ObservableList}
import javafx.scene.control.{ComboBox, Control, TextField}
import com.sfxcode.sapphire.javafx.CollectionExtensions._

import scala.jdk.CollectionConverters._

class DataFilter[S <: AnyRef](items: ObjectProperty[ObservableList[FXBean[S]]], pane: ObjectProperty[Pane])
    extends LazyLogging {
  val conf: Config = ConfigFactory.load()

  protected val controlList: ObservableList[Node] = FXCollections.observableArrayList[Node]()
  protected val controlFilterMap                  = new mutable.HashMap[Control, Any]()
  protected val controlFilterPropertyMap          = new mutable.HashMap[Control, String]()
  protected val valueMap                          = new mutable.HashMap[String, Any]()

  protected val filterControlNameMapping = new mutable.HashMap[Control, String]()
  protected val filterNameControlMapping = new mutable.HashMap[String, Control]()

  val filterResult = FXCollections.observableArrayList[FXBean[S]]()

  itemValues.addChangeListener(_ => filter())

  items.addListener { (_, _, _) =>
    itemValues.addChangeListener(_ => filter())
    filter()
  }

  items.addListener((_, oldValue, newValue) => itemsChanged(oldValue, newValue))

  def itemsChanged(oldItems: ObservableList[FXBean[S]], newItems: ObservableList[FXBean[S]]): Unit = {
    //    oldItems.addListener(_ => {
    //
    //    })
    newItems.addChangeListener(_ => filter())
    itemsHasChanged()
  }

  def itemsHasChanged(): Unit = {}

  def updateItems(newItems: ObservableList[FXBean[S]], resetFilter: Boolean = false): Unit = {
    itemsProperty.set(newItems)
    if (resetFilter)
      reset()
    else
      filter()
  }

  pane.addListener((_, oldValue, newValue) => paneChanged(oldValue, newValue))

  def paneChanged(oldPane: Pane, newPane: Pane): Unit = {
    if (oldPane != null)
      oldPane.getChildren.clear()
    newPane.getChildren.addAll(controlList)
  }

  def filterControlPane: Pane = pane.getValue

  def itemsProperty: ObjectProperty[ObservableList[FXBean[S]]] = items

  def itemValues: ObservableList[FXBean[S]] = items.getValue

  def addSearchField(propertyKey: String): TextField = addSearchField(propertyKey, propertyKey)

  def addSearchField(
      name: String,
      propertyKey: String,
      filterType: FilterValue = FilterType.FilterContainsIgnoreCase,
      searchField: TextField = TextFields.createClearableTextField()
  ): TextField =
    addCustomSearchField(name, filterFunction(filterType, propertyKey, name), searchField)

  def addCustomSearchField(
      name: String,
      p: FXBean[S] => Boolean,
      searchField: TextField = new TextField()
  ): TextField = {
    if (filterControlPane != null)
      filterControlPane.getChildren.add(searchField)
    searchField.setId(name)
    searchField.textProperty().addListener((_, oldValue, newValue) => filter())
    controlList.add(searchField)
    controlFilterMap.put(searchField, p)
    updateMapping(name, searchField).asInstanceOf[TextField]
  }

  def addSearchBox(
      name: String,
      propertyKey: String,
      noSelection: String = conf.getString("sapphire.javafx.searchBox.noSelection"),
      searchBox: ComboBox[String] = new ComboBox[String]()
  ): ComboBox[String] = {
    if (filterControlPane != null)
      filterControlPane.getChildren.add(searchBox)
    searchBox.setId(name)
    updateSearchBoxValues(searchBox, noSelection, propertyKey)
    controlList.add(searchBox)
    controlFilterPropertyMap.put(searchBox, propertyKey)
    controlFilterMap.put(searchBox, equalsFunction(propertyKey, name))
    searchBox.setOnAction(_ => filter())
    updateMapping(name, searchBox).asInstanceOf[ComboBox[String]]
  }

  def updateSearchBoxValues(searchBox: ComboBox[String], noSelection: String, propertyKey: String): Unit = {
    val distinctList = items.getValue.asScala
      .filter(b => b.getValue(propertyKey) != null)
      .map(b => b.getValue(propertyKey).toString)
      .distinct
      .sorted
    val valueBuffer = FXCollections.observableArrayList[String]()
    valueBuffer.add(noSelection)
    distinctList.toList.foreach(entry => valueBuffer.add(entry))
    searchBox.getItems.setAll(valueBuffer)
    searchBox.getSelectionModel.select(0)
  }

  def getSearchField(name: String): TextField =
    filterNameControlMapping(name).asInstanceOf[TextField]

  def getSearchBox(name: String): ComboBox[String] =
    filterNameControlMapping(name).asInstanceOf[ComboBox[String]]

  def removeFilterControl(control: Control): Unit =
    filterControlNameMapping.get(control).foreach(s => removeFilterControl(s))

  def removeFilterControl(identifier: String): Unit = {
    val control = filterNameControlMapping.get(identifier)

    control.foreach { c =>
      filterNameControlMapping.remove(identifier)
      filterControlNameMapping.remove(c)
      controlFilterMap.remove(c)
      controlFilterPropertyMap.remove(c)
      controlList.remove(c)
      pane.get.getChildren.remove(c)
    }
  }

  def removeAllControls(): Unit =
    filterNameControlMapping.keys.foreach(key => removeFilterControl(key))

  private def updateMapping(name: String, control: Control): Control = {
    filterControlNameMapping.put(control, name)
    filterNameControlMapping.put(name, control)
    control
  }

  def filter(): Unit = {
    val start    = System.currentTimeMillis()
    var filtered = FXCollections.observableArrayList(items.getValue)

    controlFilterMap.keySet.foreach {
      case textField: TextField =>
        val filter = textField.getText
        if (filter.length > 0) {
          valueMap.put(filterControlNameMapping(textField), filter)
          filtered = filtered.replaceByFilteredValues(controlFilterMap(textField).asInstanceOf[FXBean[S] => Boolean])
        }
      case searchBox: ComboBox[String] =>
        val model = searchBox.getSelectionModel
        if (model.selectedIndexProperty().get() > 0) {
          val item = model.getSelectedItem
          valueMap.put(filterControlNameMapping(searchBox), item)
          filtered = filtered.replaceByFilteredValues(controlFilterMap(searchBox).asInstanceOf[FXBean[S] => Boolean])
          logger.debug(item)
        }
      case _ =>
    }

    filterResult.clear()
    filterResult.setAll(filtered)

    logger.debug(
      "filtered [%s] (new size %d) in %d ms".format(this.hashCode(), filtered.size, System.currentTimeMillis() - start)
    )
  }

  def reset(): Unit = {
    controlFilterMap.keySet.foreach {
      case textField: TextField => textField.setText("")
      case searchBox: ComboBox[String] =>
        updateSearchBoxValues(searchBox, searchBox.getItems.get(0), controlFilterPropertyMap(searchBox))
      case _ =>
    }
    filter()
  }

  private def filterFunction(function: FilterValue, property: String, valueKey: String): FXBean[S] => Boolean =
    if (function == FilterType.FilterContains)
      containsFunction(property, valueKey)
    else if (function == FilterType.FilterContainsIgnoreCase)
      containsLowerCaseFunction(property, valueKey)
    else if (function == FilterType.FilterEquals)
      equalsFunction(property, valueKey)
    else if (function == FilterType.FilterEqualsIgnoreCase)
      equalsLowerCaseFunction(property, valueKey)
    else
      b => false

  private def containsFunction(property: String, valueKey: String): FXBean[S] => Boolean = { b =>
    getFilterString(b, property).contains(valueMap(valueKey).toString)
  }

  private def containsLowerCaseFunction(property: String, valueKey: String): FXBean[S] => Boolean = { b =>
    getFilterString(b, property).toLowerCase.contains(valueMap(valueKey).toString.toLowerCase)
  }

  private def equalsFunction(property: String, valueKey: String): FXBean[S] => Boolean = { b =>
    getFilterString(b, property).equals(valueMap(valueKey))
  }

  private def equalsLowerCaseFunction(property: String, valueKey: String): FXBean[S] => Boolean = { b =>
    getFilterString(b, property).toLowerCase.equals(valueMap(valueKey).toString.toLowerCase)
  }

  private def getFilterString(bean: FXBean[S], property: String): String = {
    val value = bean.getValue(property)
    value match {
      case d: java.util.Date     => defaultDateConverter.toString(d)
      case c: java.util.Calendar => defaultDateConverter.toString(c.getTime)
      case c: javax.xml.datatype.XMLGregorianCalendar =>
        defaultDateConverter.toString(c.toGregorianCalendar.getTime)
      case v: Any => v.toString
      case _      => ""
    }
  }
}

object DataFilter {

  def apply[S <: AnyRef](items: ObjectProperty[ObservableList[FXBean[S]]], pane: ObjectProperty[Pane]): DataFilter[S] =
    new DataFilter[S](items, pane)

}

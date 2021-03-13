package com.sfxcode.sapphire.javafx.application

import java.util.{Locale, ResourceBundle}

import com.sfxcode.sapphire.javafx.controller.SFXApplicationController
import com.sfxcode.sapphire.javafx.fxml.FxmlExpressionResolver
import com.sfxcode.sapphire.javafx.fxml.loader.{BaseDocumentLoader, DocumentLoader}
import com.sfxcode.sapphire.javafx.scene.SFXNodePropertyResolver
import com.sfxcode.sapphire.data.el.Expressions
import com.typesafe.scalalogging.LazyLogging
import javafx.collections.{FXCollections, ObservableMap}
import javafx.util.StringConverter
import javafx.util.converter.DefaultStringConverter

object SFXApplicationEnvironment extends Serializable with LazyLogging {

  val functionHelper = Expressions.functionHelper
  SFXDefaultFunctions.addDefaultFunctions(functionHelper)

  private var app: SFXApplication                     = _
  private var appController: SFXApplicationController = _

  var documentLoader: BaseDocumentLoader = new DocumentLoader

  def setApplication(application: SFXApplication): Unit = app = application
  def setApplicationController(applicationController: SFXApplicationController): Unit =
    appController = applicationController

  var wrappedApplication: SFXJavaApplication = _

  def application[T <: SFXApplication](implicit e: T DefaultsTo SFXApplication): T = app.asInstanceOf[T]

  def applicationController[T <: SFXApplicationController](implicit e: T DefaultsTo SFXApplicationController): T =
    appController.asInstanceOf[T]

  var nodePropertyResolver: SFXNodePropertyResolver = SFXNodePropertyResolver()

  var fxmlExpressionResolver = new FxmlExpressionResolver[String, Any]

  var resourceBundle: ResourceBundle = _

  val converterMap: ObservableMap[String, StringConverter[_]] =
    FXCollections.observableHashMap[String, StringConverter[_]]()

  def loadResourceBundle(path: String): Unit = {
    val classLoader = Thread.currentThread().getContextClassLoader
    resourceBundle = ResourceBundle.getBundle(path, Locale.getDefault(), classLoader)
  }

  def clearResourceBundleCache(): Unit = {
    val classLoader = Thread.currentThread().getContextClassLoader
    ResourceBundle.clearCache(classLoader)
  }

  def getConverterByName[T](name: String, forceNew: Boolean = false): StringConverter[T] = {

    var className = name
    if (!name.contains("."))
      className = "javafx.util.converter." + guessConverterName(name)

    if (!forceNew && converterMap.containsKey(className))
      converterMap.get(className).asInstanceOf[StringConverter[T]]
    else {

      var result = new DefaultStringConverter().asInstanceOf[StringConverter[T]]

      try {
        val converterClass = Class.forName(className)
        val converter      = converterClass.getDeclaredConstructor().newInstance()
        if (converter != null)
          result = converter.asInstanceOf[StringConverter[T]]
      }
      catch {
        case e: Exception =>
          logger.warn(e.getMessage)
          logger.warn("use default converter for name: " + className)
      }
      converterMap.put(className, result)
      result
    }

  }

  private def guessConverterName(className: String): String =
    if (!className.endsWith("StringConverter"))
      className + "StringConverter"
    else
      className
}

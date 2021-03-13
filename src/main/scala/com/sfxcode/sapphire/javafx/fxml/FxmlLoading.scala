package com.sfxcode.sapphire.javafx.fxml

import com.sfxcode.sapphire.data.Configuration

import java.net.URL
import java.util.ResourceBundle
import com.sfxcode.sapphire.javafx.application.SFXApplicationEnvironment
import com.sfxcode.sapphire.javafx.controller.SFXViewController
import com.sfxcode.sapphire.javafx.{ SFXConfigValues, SFXResourceBundleHolder }
import javafx.scene.layout.Pane

import scala.reflect.ClassTag

trait FxmlLoading extends Configuration {
  private lazy val recourceBundleHolder = SFXResourceBundleHolder(
    resources.getOrElse(SFXApplicationEnvironment.resourceBundle))

  var rootPane: Pane = _
  var location: Option[URL] = None
  var resources: Option[ResourceBundle] = None

  def i18n(key: String, params: Any*): String =
    recourceBundleHolder.message(key, params: _*)

  def getController[T <: SFXViewController](fxml: String = "")(implicit ct: ClassTag[T]): T = {
    val fxmlPath = guessFxmlPath(fxml, ct)

    val documentLoader = SFXApplicationEnvironment.documentLoader
    documentLoader.updateNamespace("expression", SFXApplicationEnvironment.fxmlExpressionResolver)
    val loadResult = documentLoader.loadFromDocument(fxmlPath)

    val controller = loadResult._1.asInstanceOf[T]
    controller.rootPane = loadResult._2
    controller
  }

  protected def guessFxmlPath[T <: SFXViewController](path: String, ct: ClassTag[T]): String = {
    var result = path

    // check annotatation value
    if (result.isEmpty)
      result = FxmlLocation.pathValue(ct)

    if (result.isEmpty) {
      // check configuration base path
      val basePath = configStringValue("sapphire.javafx.fxml.basePath")
      if (basePath.isEmpty) {
        // use runtime package name
        val replacementKey = configStringValue("sapphire.javafx.fxml.package.replacement.key")
        val replacementValue = configStringValue("sapphire.javafx.fxml.package.replacement.value")

        val classAsPath = ct.runtimeClass.getName.replace(replacementKey, replacementValue).replace(".", "/")
        val guessed = classAsPath.replace("Controller", "")
        result = "/%s.fxml".format(guessed)
      } else {
        val fxmlName = ct.runtimeClass.getSimpleName.replace("Controller", "")
        result = "%s%s.fxml".format(basePath, fxmlName)
      }
    }
    result
  }

  def isLoadedFromFXML: Boolean = location.isDefined

}

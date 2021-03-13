package com.sfxcode.sapphire.javafx.fxml

object FxmlLocationResolver {

  def pathValue(clazzTag: scala.reflect.ClassTag[_]): String = {
    var result       = ""
    val runtimeClass = clazzTag.runtimeClass

    if (runtimeClass.isAnnotationPresent(classOf[FxmlLocation])) {
      val location = runtimeClass.getAnnotation(classOf[FxmlLocation])
      result = location.path
    }
    result
  }

}

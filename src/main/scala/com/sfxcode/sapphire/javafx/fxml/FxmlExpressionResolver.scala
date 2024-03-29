package com.sfxcode.sapphire.javafx.fxml

import com.sfxcode.sapphire.javafx.value.SFXBean

class FxmlExpressionResolver[String, Any] extends java.util.HashMap[String, Any] {

  override def get(key: scala.Any): Any = {
    var expressionKey = key.toString
    if (containsKey(key))
      return super.get(key)
    if (expressionKey.contains("_")) {
      val parts = expressionKey.split("_")

      expressionKey = parts(0)
      (1 until parts.length).foreach(
        i => expressionKey = expressionKey + "." + parts(i) + "()")
    }

    expressionKey = "${" + expressionKey + "}"
    FxmlExpressionResolver.bean.getValue(expressionKey).asInstanceOf[Any]
  }

}

object FxmlExpressionResolver {
  val bean = new SFXBean[ExpressionBean](new ExpressionBean())

  class ExpressionBean

}

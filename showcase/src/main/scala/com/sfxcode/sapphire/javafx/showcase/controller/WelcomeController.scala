package com.sfxcode.sapphire.javafx.showcase.controller

import com.typesafe.scalalogging.LazyLogging

class WelcomeController extends BaseController with LazyLogging {

  override def startup(): Unit = {
    logMethod("startup")
  }

  override def didInitialize(): Unit = {
    logMethod("didInitialize")
  }

  override def willGainVisibility(): Unit = {
    logMethod("willGainVisibility")
  }

  override def didGainVisibility(): Unit = {
    logMethod("didGainVisibility")
  }

  override def didGainVisibilityFirstTime(): Unit = {
    logMethod("didGainVisibilityFirstTime")
  }

  override def willLooseVisibility(): Unit = {
    logMethod("willLooseVisibility")
  }

  override def didLooseVisibility(): Unit = {
    logMethod("didLooseVisibility")
  }

  def logMethod(name: String): Unit = {
    logger.info("class: %s - action: %s (root: %s, parent: %S)".format(this, name, parent, rootPane))

  }

}

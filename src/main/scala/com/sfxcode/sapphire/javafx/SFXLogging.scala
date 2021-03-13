package com.sfxcode.sapphire.javafx

import com.typesafe.scalalogging.LazyLogging

trait SFXLogging extends LazyLogging {

  def withErrorLogging(fn: => Unit): Unit =
    try fn
    catch {
      case e: Exception => logger.error(e.getMessage, e)
    }

  def withErrorLoggingReturning[T](fn: => T, defaultReturnValue: T): T = {
    var result = defaultReturnValue
    try result = fn
    catch {
      case e: Exception => logger.error(e.getMessage, e)
    }
    result
  }

}

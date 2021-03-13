package com.sfxcode.sapphire.javafx

import com.typesafe.scalalogging.LazyLogging

import scala.reflect.runtime.{universe => ru}

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

  def debugMembers(members: List[ru.Symbol]): Unit =
    logger.debug(
      members
        .collect({ case x if x.isTerm => x.asTerm })
        .filter(t => t.isVal || t.isVar)
        .map(m => m.name.toString)
        .toString()
    )

}

package com.sfxcode.sapphire.javafx

import com.sfxcode.sapphire.javafx.Configuration._
import com.sfxcode.sapphire.javafx.stage.Stage
import com.typesafe.config.{ Config, ConfigFactory }
import com.typesafe.scalalogging.LazyLogging

import scala.jdk.CollectionConverters._

trait Configuration extends Stage with LazyLogging {

  val config: Config = ConfigFactory.load()

  private def createPath(path: String): String =
    if (path.contains(StagePattern)) {

      path.replace(StagePattern, getStage)
    } else {
      path
    }

  private def configValue[E <: Any](pathValue: String, defaultReturnValue: E = None, f: String => E): E = {
    val path = createPath(pathValue)
    if (config.hasPath(path)) {
      var result = defaultReturnValue
      try result = f(path)
      catch {
        case e: Exception =>
          logger.error(e.getMessage, e)
      }
      result
    } else {
      logger.warn(ErrorMessagePattern.format(path))
      defaultReturnValue
    }
  }

  def configBooleanValue(path: String, defaultReturnValue: Boolean = false): Boolean =
    configValue[Boolean](path, defaultReturnValue, config.getBoolean)

  def configStringValue(path: String, defaultReturnValue: String = ""): String =
    configValue[String](path, defaultReturnValue, config.getString)

  def configIntValue(path: String, defaultReturnValue: Int = 0): Int =
    configValue[Int](path, defaultReturnValue, config.getInt)

  def configLongValue(path: String, defaultReturnValue: Long = 0): Long =
    configValue[Long](path, defaultReturnValue, config.getLong)

  def configDoubleValue(path: String, defaultReturnValue: Double = 0.0): Double =
    configValue[Double](path, defaultReturnValue, config.getDouble)

  def configBooleanValues(path: String): List[Boolean] =
    configValues[Boolean](path, config.getBooleanList)

  private def configValues[E <: Any](pathValue: String, f: String => java.util.List[_]): List[E] = {
    val path = createPath(pathValue)
    if (config.hasPath(path)) {
      var result = List[E]()
      try result = f(path).asScala.toList.asInstanceOf[List[E]]
      catch {
        case e: Exception =>
          logger.error(e.getMessage, e)
      }
      result
    } else {
      logger.warn(ErrorMessagePattern.format(path))
      List()
    }
  }

  def configStringValues(path: String): List[String] =
    configValues[String](path, config.getStringList)

  def configIntValues(path: String): List[Int] =
    configValues[Int](path, config.getIntList)

  def configLongValues(path: String): List[Long] =
    configValues[Long](path, config.getLongList)

  def configDoubleValues(path: String): List[Double] =
    configValues[Double](path, config.getDoubleList)

}

object Configuration {
  val ErrorMessagePattern = "config path: %s not exist"
  val StagePattern = "__stage__"
}

package com.sfxcode.sapphire.javafx.concurrent.akka

import java.util.Collections
import java.util.concurrent.{AbstractExecutorService, TimeUnit}

abstract class GUIExecutorService extends AbstractExecutorService {
  def execute(command: Runnable): Unit

  def shutdown(): Unit = ()

  def shutdownNow() = Collections.emptyList[Runnable]

  def isShutdown = false

  def isTerminated = false

  def awaitTermination(l: Long, timeUnit: TimeUnit) = true
}

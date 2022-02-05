package com.sfxcode.sapphire.javafx.concurrent.akka

import java.util
import java.util.Collections
import java.util.concurrent.{ AbstractExecutorService, TimeUnit }

abstract class SFXAbstractExecutorService extends AbstractExecutorService {
  def execute(command: Runnable): Unit

  def shutdown(): Unit = ()

  def shutdownNow(): util.List[Runnable] = Collections.emptyList[Runnable]

  def isShutdown: Boolean = false

  def isTerminated: Boolean = false

  def awaitTermination(l: Long, timeUnit: TimeUnit): Boolean = true
}

package com.sfxcode.sapphire.javafx.concurrent.akka

import java.util.concurrent.{ ExecutorService, ThreadFactory }

import akka.dispatch.{ DispatcherPrerequisites, ExecutorServiceConfigurator, ExecutorServiceFactory }
import com.typesafe.config.Config

class SFXEventThreadExecutorServiceConfigurator(config: Config, prerequisites: DispatcherPrerequisites)
  extends ExecutorServiceConfigurator(config, prerequisites) {
  private val f = new ExecutorServiceFactory {
    def createExecutorService: ExecutorService = SFXExecutorService
  }

  def createExecutorServiceFactory(id: String, threadFactory: ThreadFactory): ExecutorServiceFactory = f
}

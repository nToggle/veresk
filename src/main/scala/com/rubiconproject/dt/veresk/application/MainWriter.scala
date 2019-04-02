package com.rubiconproject.dt.veresk.application

import java.io.File
import java.time.Instant
import java.lang.Thread.UncaughtExceptionHandler

import com.rubiconproject.dt.veresk.core.S3Client
import com.typesafe.scalalogging.StrictLogging
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit._

import com.rubiconproject.dt.veresk.configuration.{
  WriterConfiguration,
  WriterStartUpConfiguration
}

object MainWriter extends StrictLogging {
  def main(args: Array[String]): Unit = {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler {
      def uncaughtException(t: Thread, e: Throwable): Unit = {
        logger.error(s"Shutting down due to uncaught exception in thread = $t",
                     e)
        System.exit(1)
      }
    })

    val (config, sampleRec) = WriterStartUpConfiguration
      .parse(args)
      .fold(sys.error("Unable to parse cli arguments")) { c =>
        WriterConfiguration.parse(c.appConfig) match {
          case Left(e) =>
            sys.error(s"Unable to parse application configuration: $e")
          case Right(pc) => (pc, c.sampleRec)
        }
      }

    val client = S3Client.buildAmazonClient(config.awsAccessKey,
                                            config.awsSecretKey,
                                            config.awsProxyHost,
                                            config.awsProxyPort)
    val fileToUpload = new File(sampleRec.getName)
    val scheduler = Executors.newSingleThreadScheduledExecutor()
    scheduler.scheduleAtFixedRate(
      new Runnable {
        def run(): Unit = {
          val timeInMillis = System.currentTimeMillis()
          val instant = Instant.ofEpochMilli(timeInMillis)
          client.putObject("test",
                           s"mapr/recommendations$instant.csvy",
                           fileToUpload)
          logger.info("Schedule" + instant)
        }
      },
      0,
      config.periodBetweenRuns,
      SECONDS
    )

    ()
  }

}

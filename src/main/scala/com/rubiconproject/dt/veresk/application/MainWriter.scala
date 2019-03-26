package com.rubiconproject.dt.veresk.application

import java.io.File

import com.rubiconproject.dt.veresk.core.S3Client
import com.typesafe.scalalogging.StrictLogging

object MainWriter extends StrictLogging {
  def main(args: Array[String]): Unit = {
    logger.info("main writer started!")

    val client = S3Client.buildAmazonClient()

    val fileToUpload = new File("src/main/resources/cartridge.csvy")
    client.putObject("test", "mapr/recommendations.csvy", fileToUpload)
    ()
  }

}

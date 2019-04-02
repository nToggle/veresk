package com.rubiconproject.dt.veresk.configuration

import java.io.File

import pureconfig.error.ConfigReaderFailures

case class WriterConfiguration(periodBetweenRuns: Long,
                               sampleRecommendationFile: String,
                               awsAccessKey: String,
                               awsSecretKey: String,
                               awsProxyHost: String,
                               awsProxyPort: Int)

object WriterConfiguration {
  def parse(path: File): Either[ConfigReaderFailures, WriterConfiguration] =
    pureconfig.loadConfig[WriterConfiguration](path.toPath)
}

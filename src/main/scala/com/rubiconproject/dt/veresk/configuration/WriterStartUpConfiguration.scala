package com.rubiconproject.dt.veresk.configuration

import java.io.File

case class WriterStartUpConfiguration(appConfig: File, sampleRec: File)

object WriterStartUpConfiguration {
  private case class RawStartUpConfiguration(appConfig: Option[File],
                                             sampleRec: Option[File])

  private val Default = RawStartUpConfiguration(None, None)

  private val parser =
    new scopt.OptionParser[RawStartUpConfiguration]("veresk") {
      head("Writer for Testing Mapr S3")

      opt[File]("app-config") valueName "<file-path>" required () action {
        (p, input) =>
          input.copy(appConfig = Some(p))
      } text "Application Configuration"

      opt[File]("sample-rec") valueName "<file-path>" required () action {
        (p, input) =>
          input.copy(appConfig = Some(p))
      } text "Sample Recommendation File to be Transferred"
    }

  def parse(input: Seq[String]): Option[WriterStartUpConfiguration] =
    parser.parse(input, Default) match {
      case Some(RawStartUpConfiguration(Some(a), Some(b))) =>
        Some(WriterStartUpConfiguration(a, b))
      case _ => None
    }
}

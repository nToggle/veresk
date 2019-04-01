object Dependencies {

  import sbt._

  object AwsClient {
    private val version = "1.11.308"

    def awsModule(module: String): ModuleID =
      ("com.amazonaws" % s"aws-java-sdk-$module" % version)
        .exclude("commons-logging", "commons-logging")

    val core = awsModule("core")
    val s3 = awsModule("s3")

    val bundle: Seq[ModuleID] = Seq(core, s3)
  }

}

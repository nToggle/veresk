import Dependencies._
import Settings._
import sbt.Keys._
import com.ntoggle.pierre.{Dependencies => PD}

lazy val commonDependencies =
  PD.withScope(Test)(PD.specsBundle :+ PD.specs2("specs2-mock")) ++
    PD.configBundle ++
    PD.loggingBundle :+
    PD.scopt

val root = (project in file("."))
  .settings(name := "veresk")
  .settings(commonSettings: _*)
  .configs(IntegrationTest extend Test)
  .settings(SparkPlugin.assemblySettings: _*)
  .settings(
    inConfig(Compile)(compile := (compile dependsOn scalafmtSbt).value)
  )
  .settings(libraryDependencies
    ++= commonDependencies
      ++ AwsClient.bundle)

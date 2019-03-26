import sbt.Keys._
import sbt._

object Settings {
  import com.ntoggle.pierre.DefaultSettings
  import org.scalafmt.sbt.ScalafmtPlugin.autoImport._
  import org.scalafmt.sbt.ScalafmtPlugin.scalafmtConfigSettings

  val testSettings: Seq[Def.Setting[_]] = Seq(
    scalacOptions in Test ++= Seq("-Yrangepos")
  )

  val formatSettings: Seq[Def.Setting[_]] =
    inConfig(IntegrationTest)(
      scalafmtConfigSettings
    ) ++ Seq(Compile, Test, IntegrationTest).flatMap {
      inConfig(_)(
        scalafmtOnCompile := true
      )
    } ++ Seq(Test, IntegrationTest).flatMap {
      inConfig(_)(
        test := (scalafmtCheck dependsOn test).value
      )
    }

  val compilerSettings: Seq[Def.Setting[_]] = Seq(
    // helps debuging pureconfig derivation errors
    scalacOptions += "-Xmacro-settings:materialize-derivations"
  )

  val commonSettings: scala.Seq[Def.Setting[_]] = (
    DefaultSettings.dtSettings()
      ++ testSettings
      ++ formatSettings
      ++ compilerSettings
    )
}
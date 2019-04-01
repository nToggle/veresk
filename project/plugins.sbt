addSbtPlugin("com.ntoggle" % "pierre" % "2.8.1")

addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.5.1")

addSbtPlugin(
  "pl.project13.scala" % "sbt-jmh" % "0.3.3" exclude ("org.ow2.asm", "asm"))

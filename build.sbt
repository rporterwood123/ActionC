name := "ActionC"

version := "1.0"

scalaVersion := "2.12.18"

libraryDependencies += "org.ow2.asm" % "asm-commons" % "9.6"

libraryDependencies += "org.parboiled" %% "parboiled-scala" % "1.3.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"

Test / parallelExecution := false

// Assembly settings for creating fat JAR
assembly / assemblyJarName := "ActionC.jar"
assembly / mainClass := Some("org.arnoldc.ArnoldC")

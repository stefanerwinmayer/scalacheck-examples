import Dependencies._
import sbtsonar.SonarPlugin.autoImport.sonarProperties

ThisBuild / scalaVersion := "2.12.9"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.stefanemayer"
ThisBuild / organizationName := "stefanemayer"
ThisBuild / scalafixDependencies += sortImports
ThisBuild / scapegoatVersion := "1.3.8"

lazy val root = (project in file("."))
  .settings(
    name := "ScalaCheck Examples",
    scalacOptions -= "-Xfatal-warnings",
    addCompilerPlugin(scalafixSemanticdb),
    scalacOptions += "-Yrangepos", // required by SemanticDB compiler plugin
    libraryDependencies ++= Seq(
      scalaTest % Test,
      scalaCheck % Test
    ),
    sonarProperties ++= Map(
      "sonar.projectKey" -> "stefanerwinmayer_scalacheck-examples",
      "sonar.organization" -> "stefanerwinmayer-github",
      "sonar.sources" -> "src/main/scala",
      "sonar.tests" -> "src/test/scala",
      "sonar.host.url" -> "https://sonarcloud.io",
      "sonar.scala.scapegoat.reportPaths" -> "target/scala-2.12/scapegoat-report/scapegoat-scalastyle.xml",
      "sonar.scala.scalastyle.reportPaths" -> "target/scalastyle-test-result.xml"
    ),
    wartremoverWarnings ++= Warts.unsafe
  )

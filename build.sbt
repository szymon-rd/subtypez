import Dependencies._

ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "subtype"
ThisBuild / organizationName := "subtype"

lazy val root = (project in file("."))
  .settings(
    name := "subtypez"
  )

libraryDependencies ++= Seq(
  scalaOrganization.value % "scala-reflect" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "3.2.9" % "test"
)

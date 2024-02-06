ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "akka-http-with-postgres"
  )

libraryDependencies += "org.postgresql" % "postgresql" % "42.5.4"
libraryDependencies += "com.typesafe" % "config" % "1.4.3"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC7"
libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor-typed_2.13" % "2.8.0",
  "com.typesafe.akka" % "akka-stream-typed_2.13" % "2.8.0",
  "com.typesafe.akka" % "akka-http_2.13" % "10.5.0",
  "com.typesafe.akka" % "akka-http-spray-json_2.13" % "10.5.0",
  "ch.qos.logback" % "logback-classic" % "1.2.6"
)
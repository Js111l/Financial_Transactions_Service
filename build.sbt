import sbt.Keys.mainClass

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "FinancialTransactionsService",
    mainClass in Compile := Some("ecom.Main")
  )
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case "reference.conf" => MergeStrategy.concat
  case x => MergeStrategy.first
}

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.akka" %% "akka-testkit" % "2.8.5",
  "com.typesafe.akka" %% "akka-protobuf-v3" % "2.8.5",
  "com.typesafe.akka" %% "akka-stream" % "2.8.5",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.5",
  "ch.megard" %% "akka-http-cors" % "1.1.2",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.5.0",
  "com.typesafe.slick" %% "slick" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.5.2",
  "net.codingwell" %% "scala-guice" % "7.0.0",
  "org.postgresql" % "postgresql" % "42.7.3",
  "com.stripe" % "stripe-java" % "24.22.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
  "org.flywaydb" % "flyway-core" % "10.20.1",
  "org.flywaydb" % "flyway-database-postgresql" % "10.20.1" % "runtime"
)

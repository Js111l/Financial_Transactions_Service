ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "FinancialTransactionsService"
  )
libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http" % "10.5.3",
    "com.typesafe.akka" %% "akka-testkit" % "2.8.5",
    "com.typesafe.akka" %% "akka-protobuf-v3" % "2.8.5",
    "com.typesafe.akka" %% "akka-serialization-jackson" % "2.8.5",
    "com.typesafe.akka" %% "akka-stream" % "2.8.5",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.5",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
    "com.typesafe.slick" %% "slick" % "3.4.1",
    "org.slf4j" % "slf4j-nop" % "2.0.9",
    "ch.qos.logback" % "logback-classic" % "1.4.14",
    "net.codingwell" %% "scala-guice" % "7.0.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
    "org.postgresql" % "postgresql" % "42.7.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
   // "org.flywaydb" %% "flyway-core" % "8.0.0"
)

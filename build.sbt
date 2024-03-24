ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "FinancialTransactionsService"
  )
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.5.0",
  "com.typesafe.akka" %% "akka-testkit" % "2.8.5",
  "com.typesafe.akka" %% "akka-protobuf-v3" % "2.8.5",
  "com.typesafe.akka" %% "akka-serialization-jackson" % "2.8.5",
  "com.typesafe.akka" %% "akka-stream" % "2.8.5",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.5",
  "com.h2database" % "h2" % "2.1.214",
  "io.spray" %% "spray-json" % "1.3.6",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
  "com.typesafe.slick" %% "slick" % "3.4.1",
  "org.slf4j" % "slf4j-nop" % "2.0.5",
  "ch.qos.logback" % "logback-classic" % "1.4.7",
  "com.stripe" % "stripe-java" % "24.22.0-beta.1",
  "net.codingwell" %% "scala-guice" % "7.0.0"

)


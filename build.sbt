val catsEffectVersion = "3.3.6"
val circeVersion = "0.14.1"
val doobieVersion = "1.0.0-RC1"
val http4sVersion = "0.23.6"
val bouncyCastleVersion = "1.69"
val http4sJwtVersion = "1.1.0"
val pureconfigVersion = "0.17.0"
val postgresVersion = "42.2.23"
val scalatestVersion = "3.2.9"
val h2Version = "1.4.200"
val doobieH2Version = "1.0.0-RC1"
val scalatestDoobieVersion = "0.10.0"
val scalatestPlusScalacheckVersion = "3.1.0.0-RC2"
val weaverVersion = "0.8.1"
val flywayVersion = "9.16.0"
val sttpOauth2Version = "0.16.0"
val logbackVersion = "1.2.6"

lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(
        organization := "com.cryptomap",
        scalaVersion := "2.13.8"
      )
    ),
    name := "CryptoMapAPI",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.postgresql" % "postgresql" % postgresVersion,
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "org.tpolecat" %% "doobie-core" % doobieVersion,
      "org.tpolecat" %% "doobie-postgres" % doobieVersion,
      "org.tpolecat" %% "doobie-hikari" % doobieVersion,
      "org.bouncycastle" % "bcprov-jdk15on" % bouncyCastleVersion,
      "com.github.pureconfig" %% "pureconfig" % pureconfigVersion,
      "com.github.pureconfig" %% "pureconfig-cats-effect" % pureconfigVersion,
      "com.github.t3hnar" %% "scala-bcrypt" % "4.3.0",
      "org.flywaydb" % "flyway-core" % flywayVersion,
      "com.ocadotechnology" %% "sttp-oauth2" % sttpOauth2Version,
      "ch.qos.logback" % "logback-classic" % logbackVersion,
      "com.h2database" % "h2" % h2Version % Test,
      "org.tpolecat" %% "doobie-h2" % doobieH2Version % Test,
      "com.disneystreaming" %% "weaver-cats" % weaverVersion % Test,
      "com.disneystreaming" %% "weaver-scalacheck" % weaverVersion % Test
    )
  )

resolvers += "Custom Repository" at "https://github.com/trace4cats/trace4cats"

testFrameworks += new TestFramework("weaver.framework.CatsEffect")

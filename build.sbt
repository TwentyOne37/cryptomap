val catsEffectVersion = "3.3.6"
val circeVersion = "0.14.1"
val doobieVersion = "1.0.0-RC1"
val http4sVersion = "0.23.6"
val bouncyCastleVersion = "1.69"
val http4sJwtVersion = "1.1.0"
val pureconfigVersion = "0.17.0"
val postgresVersion = "42.2.23"

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
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "org.postgresql" % "postgresql" % postgresVersion,
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "org.tpolecat" %% "doobie-core" % doobieVersion,
      "org.tpolecat" %% "doobie-postgres" % doobieVersion,
      "org.tpolecat" %% "doobie-hikari" % doobieVersion,
      "org.bouncycastle" % "bcprov-jdk15on" % bouncyCastleVersion,
      "com.github.pureconfig" %% "pureconfig" % pureconfigVersion,
      "com.github.pureconfig" %% "pureconfig-cats-effect" % pureconfigVersion
    )
  )

resolvers += "Custom Repository" at "https://github.com/trace4cats/trace4cats"

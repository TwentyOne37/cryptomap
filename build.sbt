name := "CryptoMapAPI"

version := "0.1.0"

scalaVersion := "2.13.7"

val http4sVersion = "1.0.0-M29"
val doobieVersion = "1.0.0-RC1"
val circeVersion = "0.14.1"
val tsecVersion = "0.2.1"

libraryDependencies ++= Seq(
  // http4s
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,

  // doobie
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-hikari" % doobieVersion,

  // circe
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  // cats
  "org.typelevel" %% "cats-core" % "2.7.0",
  "org.typelevel" %% "cats-effect" % "3.3.0",

  // tsec (authentication & authorization)
  "io.github.jmcardon" %% "tsec-common" % tsecVersion,
  "io.github.jmcardon" %% "tsec-password" % tsecVersion,
  "io.github.jmcardon" %% "tsec-http4s" % tsecVersion,

  // testing
  "org.scalatest" %% "scalatest" % "3.2.10" % Test
)

// Add this line to use the ScalaTest runner to run tests
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")

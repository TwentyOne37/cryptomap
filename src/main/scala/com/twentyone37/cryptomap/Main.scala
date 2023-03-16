package com.twentyone37.cryptomap

import cats.effect._
import com.twentyone37.cryptomap.routes.AuthRoutes
import com.twentyone37.cryptomap.services.UserService
import com.twentyone37.cryptomap.repository.UserRepository
import org.http4s.HttpRoutes
import org.http4s.implicits._
import org.http4s.blaze.server._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val userRepository = UserRepository.impl[IO]
    val userService = UserService[IO](userRepository)
    val app = createApp(userService)

    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(app.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }

  private def createApp(userService: UserService[IO]): HttpRoutes[IO] = {
    val authRoutes = AuthRoutes(userService).routes

    authRoutes
  }
}

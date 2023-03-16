package com.twentyone37.cryptomap.routes

import cats.effect.Sync
import cats.syntax.flatMap._
import com.twentyone37.cryptomap.models.{LoginRequest, RegisterRequest, User}
import com.twentyone37.cryptomap.services.UserService
import io.circe.syntax._
import org.http4s._
import org.http4s.dsl.Http4sDsl

class AuthRoutes[F[_]: Sync](userService: UserService[F]) extends Http4sDsl[F] {
  val routes: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ POST -> Root / "login" =>
      req.decode[LoginRequest] { loginReq =>
        val maybeUser =
          userService.login(loginReq.username, loginReq.password)
        maybeUser.flatMap {
          case Some(user) => Ok(user.asJson)
          case None       => Forbidden()
        }
      }

    case req @ POST -> Root / "register" =>
      req.decode[RegisterRequest] { registerReq =>
        userService
          .register(registerReq.username, registerReq.password)
          .flatMap(user => Created(user.asJson))
      }
  }
}

object AuthRoutes {
  def apply[F[_]: Sync](userService: UserService[F]): AuthRoutes[F] =
    new AuthRoutes[F](userService)
}

package com.twentyone37.cryptomap.application.services

import cats.effect.Concurrent
import cats.syntax.flatMap._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import org.http4s._
import org.http4s.dsl.Http4sDsl
import com.twentyone37.cryptomap.domain.user._

class AuthRoutes[F[_]: Concurrent](userService: UserService[F])
    extends Http4sDsl[F] {

  implicit val loginRequestDecoder: EntityDecoder[F, LoginRequest] =
    jsonOf[F, LoginRequest]
  implicit val registerRequestDecoder: EntityDecoder[F, RegisterRequest] =
    jsonOf[F, RegisterRequest]

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
          .register(
            registerReq.username,
            registerReq.password,
            registerReq.email
          )
          .flatMap(user => Created(user.asJson))
      }
  }
}

object AuthRoutes {
  def apply[F[_]: Concurrent](userService: UserService[F]): AuthRoutes[F] =
    new AuthRoutes[F](userService)
}

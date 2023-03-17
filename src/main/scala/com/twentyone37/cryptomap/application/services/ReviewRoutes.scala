package com.twentyone37.cryptomap.routes

import cats.effect._
import org.http4s._
import org.http4s.dsl.Http4sDsl

object ReviewRoutes {
  def apply[F[_]: Async](): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "review" =>
        // Handle GET /review request
        ???

      case GET -> Root / "review" / LongVar(_) =>
        // Handle GET /review/:id request
        ???

      case _ @POST -> Root / "review" =>
        // Handle POST /review request
        ???

      case _ @PUT -> Root / "review" / LongVar(_) =>
        // Handle PUT /review/:id request
        ???

      case DELETE -> Root / "review" / LongVar(_) =>
        // Handle DELETE /review/:id request
        ???
    }
  }
}

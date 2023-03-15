package com.twentyone37.cryptomap.routes

import cats.effect._
import org.http4s._
import org.http4s.dsl.Http4sDsl

object TransactionRoutes {
  def apply[F[_]: Async](): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "transactions" =>
        // Handle GET /transactions request
        ???

      case GET -> Root / "transactions" / LongVar(id) =>
        // Handle GET /transactions/:id request
        ???

      case req @ POST -> Root / "transactions" =>
        // Handle POST /transactions request
        ???

      case req @ PUT -> Root / "transactions" / LongVar(id) =>
        // Handle PUT /transactions/:id request
        ???

      case DELETE -> Root / "transactions" / LongVar(id) =>
        // Handle DELETE /transactions/:id request
        ???
    }
  }
}

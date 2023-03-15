package com.twentyone37.cryptomap.routes

import cats.effect._
import org.http4s._
import org.http4s.dsl.Http4sDsl

object MerchantRoutes {
  def apply[F[_]: Async](): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "merchants" =>
        // Handle GET /merchants request
        ???

      case GET -> Root / "merchants" / LongVar(id) =>
        // Handle GET /merchants/:id request
        ???

      case req @ POST -> Root / "merchants" =>
        // Handle POST /merchants request
        ???

      case req @ PUT -> Root / "merchants" / LongVar(id) =>
        // Handle PUT /merchants/:id request
        ???

      case DELETE -> Root / "merchants" / LongVar(id) =>
        // Handle DELETE /merchants/:id request
        ???
    }
  }
}

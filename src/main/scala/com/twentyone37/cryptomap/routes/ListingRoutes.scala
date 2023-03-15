package com.twentyone37.cryptomap.routes

import cats.effect._
import org.http4s._
import org.http4s.dsl.Http4sDsl

object ListingRoutes {
  def apply[F[_]: Async](): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "listings" =>
        // Handle GET /listings request
        ???

      case GET -> Root / "listings" / LongVar(id) =>
        // Handle GET /listings/:id request
        ???

      case req @ POST -> Root / "listings" =>
        // Handle POST /listings request
        ???

      case req @ PUT -> Root / "listings" / LongVar(id) =>
        // Handle PUT /listings/:id request
        ???

      case DELETE -> Root / "listings" / LongVar(id) =>
        // Handle DELETE /listings/:id request
        ???
    }
  }
}

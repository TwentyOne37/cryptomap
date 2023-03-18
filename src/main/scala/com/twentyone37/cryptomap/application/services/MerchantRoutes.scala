package com.twentyone37.cryptomap.application.services

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

      case GET -> Root / "merchants" / LongVar(_) =>
        // Handle GET /merchants/:id request
        ???

      case _ @POST -> Root / "merchants" =>
        // Handle POST /merchants request
        ???

      case _ @PUT -> Root / "merchants" / LongVar(_) =>
        // Handle PUT /merchants/:id request
        ???

      case DELETE -> Root / "merchants" / LongVar(_) =>
        // Handle DELETE /merchants/:id request
        ???
    }
  }
}

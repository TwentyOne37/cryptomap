package com.twentyone37.cryptomap.application.services

import cats.effect.Async
import cats.data.OptionT
import org.http4s._
import org.http4s.dsl.Http4sDsl
import com.twentyone37.cryptomap.domain.merchant._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._

object MerchantRoutes {

  def routes[F[_]: Async](
      merchantService: MerchantService[F]
  ): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "merchants" =>
        Ok(merchantService.list())

      case GET -> Root / "merchants" / LongVar(id) =>
        (for {
          merchant <- OptionT(merchantService.get(id))
          response <- OptionT.liftF(Ok(merchant))
        } yield response)
          .getOrElseF(NotFound(s"Merchant not found with id: $id"))

      case req @ POST -> Root / "merchants" =>
        Async[F].flatMap(req.as[NewMerchant]) { merchant =>
          Created(merchantService.create(merchant))
        }

      case req @ PUT -> Root / "merchants" / LongVar(id) =>
        Async[F].flatMap(req.as[Merchant]) { updatedMerchant =>
          (for {
            merchant <- OptionT(
              merchantService.update(updatedMerchant.copy(id = id))
            )
            response <- OptionT.liftF(Ok(merchant))
          } yield response)
            .getOrElseF(NotFound(s"Merchant not found with id: $id"))
        }

      case DELETE -> Root / "merchants" / LongVar(id) =>
        Async[F].flatMap(merchantService.delete(id)) {
          case true  => NoContent()
          case false => NotFound(s"Merchant not found with id: $id")
        }
    }
  }
}

package com.twentyone37.cryptomap.application.services

import cats.effect.Async
import cats.data.OptionT
import org.http4s._
import org.http4s.dsl.Http4sDsl
import com.twentyone37.cryptomap.domain.listing._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.EntityEncoder._

object ListingRoutes {

  def routes[F[_]: Async](
      listingService: ListingService[F]
  ): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "listings" =>
        Ok(listingService.list())

      case GET -> Root / "listings" / LongVar(id) =>
        (for {
          listing <- OptionT(listingService.get(id))
          response <- OptionT.liftF(Ok(listing))
        } yield response)
          .getOrElseF(NotFound(s"Listing not found with id: $id"))

      case req @ POST -> Root / "listings" =>
        Async[F].flatMap(req.as[Listing]) { listing =>
          Created(listingService.create(listing))
        }

      case req @ PUT -> Root / "listings" / LongVar(id) =>
        Async[F].flatMap(req.as[Listing]) { updatedListing =>
          (for {
            listing <- OptionT(
              listingService.update(updatedListing.copy(id = id))
            )
            response <- OptionT.liftF(Ok(listing))
          } yield response)
            .getOrElseF(NotFound(s"Listing not found with id: $id"))
        }

      case DELETE -> Root / "listings" / LongVar(id) =>
        Async[F].flatMap(listingService.delete(id)) {
          case true  => NoContent()
          case false => NotFound(s"Listing not found with id: $id")
        }
    }
  }
}

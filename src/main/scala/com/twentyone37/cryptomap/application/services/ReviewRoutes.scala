package com.twentyone37.cryptomap.application.services

import cats.effect.Async
import cats.data.OptionT
import org.http4s._
import org.http4s.dsl.Http4sDsl
import com.twentyone37.cryptomap.domain.review._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._

object ReviewRoutes {
  def routes[F[_]: Async](reviewService: ReviewService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "reviews" =>
        Ok(reviewService.list())

      case GET -> Root / "reviews" / LongVar(id) =>
        (for {
          review <- OptionT(reviewService.get(id))
          response <- OptionT.liftF(Ok(review))
        } yield response).getOrElseF(NotFound(s"Review not found with id: $id"))

      case req @ POST -> Root / "reviews" =>
        Async[F].flatMap(req.as[NewReview]) { review =>
          Created(reviewService.create(review))
        }

      case req @ PUT -> Root / "reviews" / LongVar(id) =>
        Async[F].flatMap(req.as[Review]) { updatedReview =>
          (for {
            review <- OptionT(reviewService.update(updatedReview.copy(id = id)))
            response <- OptionT.liftF(Ok(review))
          } yield response)
            .getOrElseF(NotFound(s"Review not found with id: $id"))
        }

      case DELETE -> Root / "reviews" / LongVar(id) =>
        Async[F].flatMap(reviewService.delete(id)) {
          case true  => NoContent()
          case false => NotFound(s"Review not found with id: $id")
        }
    }
  }
}

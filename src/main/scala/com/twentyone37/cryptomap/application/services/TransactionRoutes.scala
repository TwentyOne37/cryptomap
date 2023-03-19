package com.twentyone37.cryptomap.application.services

import cats.effect.Async
import cats.data.OptionT
import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._
import com.twentyone37.cryptomap.domain.transaction._

object TransactionRoutes {
  def routes[F[_]: Async](
      transactionService: TransactionService[F]
  ): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "transactions" =>
        Ok(transactionService.list())

      case GET -> Root / "transactions" / LongVar(id) =>
        (for {
          transaction <- OptionT(transactionService.get(id))
          response <- OptionT.liftF(Ok(transaction))
        } yield response)
          .getOrElseF(NotFound(s"Transaction not found with id: $id"))

      case req @ POST -> Root / "transactions" =>
        Async[F].flatMap(req.as[Transaction]) { transaction =>
          Created(transactionService.create(transaction))
        }

      case req @ PUT -> Root / "transactions" / LongVar(id) =>
        Async[F].flatMap(req.as[Transaction]) { updatedTransaction =>
          (for {
            transaction <- OptionT(
              transactionService.update(updatedTransaction.copy(id = id))
            )
            response <- OptionT.liftF(Ok(transaction))
          } yield response)
            .getOrElseF(NotFound(s"Transaction not found with id: $id"))
        }

      case DELETE -> Root / "transactions" / LongVar(id) =>
        Async[F].flatMap(transactionService.delete(id)) {
          case true  => NoContent()
          case false => NotFound(s"Review not found with id: $id")
        }
    }
  }
}

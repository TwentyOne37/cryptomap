package com.twentyone37.cryptomap.domain.listing

import cats.effect.Async
import io.circe._
import io.circe.generic.semiauto._
import org.http4s._
import org.http4s.circe._
import com.twentyone37.cryptomap.models.currencies.Crypto

import scala.annotation.nowarn
import java.time.LocalDateTime

// todo: updatedAt should be optional
case class Listing(
    id: Long,
    title: String,
    description: String,
    price: BigDecimal,
    crypto: Crypto,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
)

object Listing {
  import com.twentyone37.cryptomap.models.currencies.Crypto._

  implicit val listingDecoder: Decoder[Listing] = deriveDecoder[Listing]
  implicit val listingEncoder: Encoder[Listing] = deriveEncoder[Listing]

  implicit def listingEntityDecoder[F[_]: Async]: EntityDecoder[F, Listing] =
    jsonOf[F, Listing]
  @nowarn // suppress warning about unused Async
  implicit def listingEntityEncoder[F[_]: Async]: EntityEncoder[F, Listing] =
    jsonEncoderOf[F, Listing]
}

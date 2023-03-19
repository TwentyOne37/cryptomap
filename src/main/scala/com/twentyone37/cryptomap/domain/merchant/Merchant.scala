package com.twentyone37.cryptomap.domain.merchant

import cats.effect.Async
import io.circe._
import io.circe.generic.semiauto._
import org.http4s._
import org.http4s.circe._
import com.twentyone37.cryptomap.models.Location

case class Merchant(id: Long, name: String, email: String, location: Location)

object Merchant {
  import com.twentyone37.cryptomap.models.Location._

  implicit val merchantDecoder: Decoder[Merchant] = deriveDecoder[Merchant]
  implicit val merchantEncoder: Encoder[Merchant] = deriveEncoder[Merchant]

  implicit def merchantEntityDecoder[F[_]: Async]: EntityDecoder[F, Merchant] =
    jsonOf[F, Merchant]
  implicit def merchantEntityEncoder[F[_]]: EntityEncoder[F, Merchant] =
    jsonEncoderOf[F, Merchant]
}

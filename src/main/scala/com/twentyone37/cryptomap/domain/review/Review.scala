package com.twentyone37.cryptomap.domain.review

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class Review(
    id: Long,
    rating: Int,
    comment: String,
    userId: Long,
    merchantId: Long
)

object Review {
  implicit val reviewDecoder: Decoder[Review] = deriveDecoder[Review]
  implicit val reviewEncoder: Encoder[Review] = deriveEncoder[Review]
}

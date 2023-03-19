package com.twentyone37.cryptomap.domain.review

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class NewReview(
    rating: Int,
    comment: String,
    userId: Long,
    merchantId: Long
) {
  val now = java.time.LocalDateTime.now()
  def toReview(id: Long): Review = {
    Review(id, rating, comment, userId, merchantId)
  }
}

object NewReview {
  implicit val newReviewDecoder: Decoder[NewReview] = deriveDecoder[NewReview]
  implicit val newReviewEncoder: Encoder[NewReview] = deriveEncoder[NewReview]
}

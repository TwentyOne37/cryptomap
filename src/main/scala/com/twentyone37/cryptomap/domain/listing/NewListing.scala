package com.twentyone37.cryptomap.domain.listing

import com.twentyone37.cryptomap.models.currencies.Crypto

case class NewListing(
    title: String,
    description: String,
    price: Double,
    crypto: Crypto
) {
  val now = java.time.LocalDateTime.now()
  def toListing(id: Long): Listing = {
    Listing(id, title, description, price, crypto, now, now)
  }

}

object NewListing {
  import io.circe._
  import io.circe.generic.semiauto._
  import com.twentyone37.cryptomap.models.currencies.Crypto._

  implicit val newListingDecoder: Decoder[NewListing] =
    deriveDecoder[NewListing]
  implicit val newListingEncoder: Encoder[NewListing] =
    deriveEncoder[NewListing]
}

package com.twentyone37.cryptomap.domain.merchant

import io.circe._
import io.circe.generic.semiauto._
import com.twentyone37.cryptomap.models.Location

case class NewMerchant(name: String, email: String, location: Location) {
  def toMerchant(id: Long): Merchant = Merchant(id, name, email, location)
}

object NewMerchant {
  implicit val newMerchantDecoder: Decoder[NewMerchant] =
    deriveDecoder[NewMerchant]
  implicit val newMerchantEncoder: Encoder[NewMerchant] =
    deriveEncoder[NewMerchant]
}

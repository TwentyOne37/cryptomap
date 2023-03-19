package com.twentyone37.cryptomap.models

import io.circe._
import io.circe.generic.semiauto._

case class Location(
    address: String,
    latitude: Double,
    longitude: Double
)

object Location {
  implicit val locationDecoder: Decoder[Location] = deriveDecoder[Location]
  implicit val locationEncoder: Encoder[Location] = deriveEncoder[Location]
}

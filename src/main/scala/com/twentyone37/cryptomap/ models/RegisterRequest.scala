package com.twentyone37.cryptomap.models

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class RegisterRequest(username: String, password: String)

object RegisterRequest {
  implicit val registerRequestEncoder: Encoder[RegisterRequest] =
    deriveEncoder[RegisterRequest]
  implicit val registerRequestDecoder: Decoder[RegisterRequest] =
    deriveDecoder[RegisterRequest]
}

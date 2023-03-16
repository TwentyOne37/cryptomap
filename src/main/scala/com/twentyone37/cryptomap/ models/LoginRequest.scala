package com.twentyone37.cryptomap.models

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class LoginRequest(username: String, password: String)

object LoginRequest {
  implicit val loginRequestEncoder: Encoder[LoginRequest] =
    deriveEncoder[LoginRequest]
  implicit val loginRequestDecoder: Decoder[LoginRequest] =
    deriveDecoder[LoginRequest]
}

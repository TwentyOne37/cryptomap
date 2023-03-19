package com.twentyone37.cryptomap.domain.transaction

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class Transaction(
    id: Long,
    amount: BigDecimal,
    userId: Long,
    listingId: Long
)

object Transaction {
  implicit val transactionDecoder: Decoder[Transaction] =
    deriveDecoder[Transaction]
  implicit val transactionEncoder: Encoder[Transaction] =
    deriveEncoder[Transaction]
}

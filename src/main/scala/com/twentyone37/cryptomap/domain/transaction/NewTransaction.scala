package com.twentyone37.cryptomap.domain.transaction

import io.circe._
import io.circe.generic.semiauto._

case class NewTransaction(amount: Double, userId: Long, listingId: Long) {
  def toTransaction(id: Long): Transaction =
    Transaction(id, amount, userId, listingId)
}

object NewTransaction {
  implicit val newTransactionDecoder: Decoder[NewTransaction] =
    deriveDecoder[NewTransaction]
  implicit val newTransactionEncoder: Encoder[NewTransaction] =
    deriveEncoder[NewTransaction]
}

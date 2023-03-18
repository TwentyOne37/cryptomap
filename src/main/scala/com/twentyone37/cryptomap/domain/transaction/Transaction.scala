package com.twentyone37.cryptomap.domain.transaction

case class Transaction(
    id: Long,
    amount: BigDecimal,
    userId: Long,
    listingId: Long
)

package com.twentyone37.cryptomap.models

case class Transaction(
    id: Long,
    amount: BigDecimal,
    userId: Long,
    listingId: Long
)

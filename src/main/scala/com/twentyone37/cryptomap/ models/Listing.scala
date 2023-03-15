package com.twentyone37.cryptomap.models

case class Listing(
    id: Long,
    title: String,
    description: String,
    price: BigDecimal,
    merchantId: Long
)

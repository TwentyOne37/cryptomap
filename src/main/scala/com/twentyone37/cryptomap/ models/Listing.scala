package com.twentyone37.cryptomap.models

import com.twentyone37.cryptomap.models.currencies.Crypto

import java.math.BigDecimal

case class Listing(
    id: Long,
    title: String,
    description: Option[String],
    price: BigDecimal,
    currency: Crypto,
    merchantId: Long
)

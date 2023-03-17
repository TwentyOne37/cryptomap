package com.twentyone37.cryptomap.models

case class Review(
    id: Long,
    rating: Int,
    comment: String,
    userId: Long,
    merchantId: Long
)

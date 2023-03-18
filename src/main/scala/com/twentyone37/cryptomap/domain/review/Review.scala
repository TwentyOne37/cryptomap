package com.twentyone37.cryptomap.domain.review

case class Review(
    id: Long,
    rating: Int,
    comment: String,
    userId: Long,
    merchantId: Long
)

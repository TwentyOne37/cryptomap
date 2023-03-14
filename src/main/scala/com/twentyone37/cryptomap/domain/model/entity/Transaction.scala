package com.twentyone37.cryptomap.domain.model.entity

case class Transaction(id: Long, buyerId: Long, sellerId: Long, amount: Double)
    extends Entity

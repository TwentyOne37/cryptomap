package com.twentyone37.cryptomap.domain.model.entity

case class Receipt(id: Long, transactionId: Long, timestamp: Long)
    extends Entity

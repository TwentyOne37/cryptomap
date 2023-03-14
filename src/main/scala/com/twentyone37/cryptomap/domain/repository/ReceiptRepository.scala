package com.twentyone37.cryptomap.domain.repository

import com.twentyone37.cryptomap.domain.model.entity.Receipt

object ReceiptRepository extends Repository[Receipt] {
  private var receipts: Map[Long, Receipt] = Map()

  def getById(id: Long): Option[Receipt] = receipts.get(id)
  def getAll: List[Receipt] = receipts.values.toList
  def add(receipt: Receipt): Unit =
    receipts += (receipt.id -> receipt)
  def update(receipt: Receipt): Unit =
    receipts += (receipt.id -> receipt)
  def delete(receipt: Receipt): Unit =
    receipts -= receipt.id
}

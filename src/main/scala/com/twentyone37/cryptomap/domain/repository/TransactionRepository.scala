package com.twentyone37.cryptomap.domain.repository

import com.twentyone37.cryptomap.domain.model.entity.Transaction

object TransactionRepository extends Repository[Transaction] {
  private var transactions: Map[Long, Transaction] = Map()

  def getById(id: Long): Option[Transaction] = transactions.get(id)
  def getAll: List[Transaction] = transactions.values.toList
  def add(transaction: Transaction): Unit =
    transactions += (transaction.id -> transaction)
  def update(transaction: Transaction): Unit =
    transactions += (transaction.id -> transaction)
  def delete(transaction: Transaction): Unit =
    transactions -= transaction.id
}

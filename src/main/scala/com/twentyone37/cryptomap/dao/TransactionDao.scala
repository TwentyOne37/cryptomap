package com.twentyone37.cryptomap.dao

import com.twentyone37.cryptomap.models.Transaction

trait TransactionDao {
  def get(id: Long): Option[Transaction]
  def list(): List[Transaction]
  def create(transaction: Transaction): Transaction
  def update(transaction: Transaction): Option[Transaction]
  def delete(id: Long): Boolean
}

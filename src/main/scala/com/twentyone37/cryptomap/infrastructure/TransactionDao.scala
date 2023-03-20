package com.twentyone37.cryptomap.infrastructure

import com.twentyone37.cryptomap.domain.transaction.{
  NewTransaction,
  Transaction
}

trait TransactionDao[F[_]] {
  def get(id: Long): F[Option[Transaction]]
  def list(): F[List[Transaction]]
  def create(transaction: NewTransaction): F[Transaction]
  def update(transaction: Transaction): F[Option[Transaction]]
  def delete(id: Long): F[Boolean]
}

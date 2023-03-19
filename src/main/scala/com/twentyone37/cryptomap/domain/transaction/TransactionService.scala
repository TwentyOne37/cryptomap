package com.twentyone37.cryptomap.domain.transaction

trait TransactionService[F[_]] {
  def get(id: Long): F[Option[Transaction]]
  def list(): F[List[Transaction]]
  def create(transaction: Transaction): F[Transaction]
  def update(transaction: Transaction): F[Option[Transaction]]
  def delete(id: Long): F[Boolean]
}

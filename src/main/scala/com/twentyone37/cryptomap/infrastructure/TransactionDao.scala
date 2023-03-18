package com.twentyone37.cryptomap.infrastructure

import cats.effect.IO
import com.twentyone37.cryptomap.domain.transaction.Transaction

trait TransactionDao {
  def get(id: Long): IO[Option[Transaction]]
  def list(): IO[List[Transaction]]
  def create(transaction: Transaction): IO[Transaction]
  def update(transaction: Transaction): IO[Option[Transaction]]
  def delete(id: Long): IO[Boolean]
}

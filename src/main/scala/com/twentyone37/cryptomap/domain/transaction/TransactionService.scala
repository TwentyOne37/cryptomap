package com.twentyone37.cryptomap.domain.transaction

import cats.effect.IO

trait TransactionService {
  def get(id: Long): IO[Option[Transaction]]
  def list(): IO[List[Transaction]]
  def create(transaction: Transaction): IO[Transaction]
  def update(transaction: Transaction): IO[Option[Transaction]]
  def delete(id: Long): IO[Boolean]
}

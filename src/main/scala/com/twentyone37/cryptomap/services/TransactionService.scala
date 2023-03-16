package com.twentyone37.cryptomap.services

import cats.effect.IO
import com.twentyone37.cryptomap.models.Transaction

trait TransactionService {
  def get(id: Long): IO[Option[Transaction]]
  def list(): IO[List[Transaction]]
  def create(transaction: Transaction): IO[Transaction]
  def update(transaction: Transaction): IO[Option[Transaction]]
  def delete(id: Long): IO[Boolean]
}

package com.twentyone37.cryptomap.services

import cats.effect.IO
import com.twentyone37.cryptomap.models.Transaction
import com.twentyone37.cryptomap.dao.TransactionDao

class TransactionService(transactionDao: TransactionDao) {
  def get(id: Long): IO[Option[Transaction]] = transactionDao.get(id)
  def list(): IO[List[Transaction]] = transactionDao.list()
  def create(transaction: Transaction): IO[Transaction] =
    transactionDao.create(transaction)
  def update(transaction: Transaction): IO[Option[Transaction]] =
    transactionDao.update(transaction)
  def delete(id: Long): IO[Boolean] = transactionDao.delete(id)
}

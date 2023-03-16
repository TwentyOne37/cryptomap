package com.twentyone37.cryptomap.services

import cats.effect.IO
import com.twentyone37.cryptomap.models.Transaction
import com.twentyone37.cryptomap.dao.TransactionDao

class TransactionServiceImpl(transactionDao: TransactionDao)
    extends TransactionService {
  override def get(id: Long): IO[Option[Transaction]] = transactionDao.get(id)
  override def list(): IO[List[Transaction]] = transactionDao.list()
  override def create(transaction: Transaction): IO[Transaction] =
    transactionDao.create(transaction)
  override def update(transaction: Transaction): IO[Option[Transaction]] =
    transactionDao.update(transaction)
  override def delete(id: Long): IO[Boolean] = transactionDao.delete(id)
}

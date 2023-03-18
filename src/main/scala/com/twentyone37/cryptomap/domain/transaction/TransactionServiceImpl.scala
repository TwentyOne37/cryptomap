package com.twentyone37.cryptomap.domain.transaction

import cats.effect.IO
import com.twentyone37.cryptomap.infrastructure.TransactionDao

import com.twentyone37.cryptomap.domain.transaction.TransactionService
import com.twentyone37.cryptomap.domain.transaction.Transaction
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

package com.twentyone37.cryptomap.domain.transaction

import com.twentyone37.cryptomap.infrastructure.TransactionDao

class TransactionServiceImpl[F[_]](transactionDao: TransactionDao[F])
    extends TransactionService[F] {
  override def get(id: Long): F[Option[Transaction]] = transactionDao.get(id)
  override def list(): F[List[Transaction]] = transactionDao.list()
  override def create(transaction: NewTransaction): F[Transaction] =
    transactionDao.create(transaction)
  override def update(transaction: Transaction): F[Option[Transaction]] =
    transactionDao.update(transaction)
  override def delete(id: Long): F[Boolean] = transactionDao.delete(id)
}

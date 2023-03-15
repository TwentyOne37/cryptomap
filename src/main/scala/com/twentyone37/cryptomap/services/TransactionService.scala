package com.twentyone37.cryptomap.services

import com.twentyone37.cryptomap.models.Transaction

trait TransactionService[F[_]] {
  def getTransactions: F[List[Transaction]]
  def getTransaction(id: Long): F[Option[Transaction]]
  def createTransaction(transaction: Transaction): F[Transaction]
  def updateTransaction(
      id: Long,
      transaction: Transaction
  ): F[Option[Transaction]]
  def deleteTransaction(id: Long): F[Option[Unit]]
}

package com.twentyone37.cryptomap.infrastructure

import cats.effect.Sync
import doobie._
import doobie.implicits._
import com.twentyone37.cryptomap.domain.transaction.{
  Transaction,
  NewTransaction
}

class TransactionDaoImpl[F[_]: Sync](transactor: Transactor[F])
    extends TransactionDao[F] {
  override def get(id: Long): F[Option[Transaction]] = {
    sql"SELECT id, amount, user_id, listing_id FROM transactions WHERE id = $id"
      .query[Transaction]
      .option
      .transact(transactor)
  }

  override def list(): F[List[Transaction]] = {
    sql"SELECT id, amount, user_id, listing_id FROM transactions"
      .query[Transaction]
      .to[List]
      .transact(transactor)
  }

  override def create(newTransaction: NewTransaction): F[Transaction] = {
    sql"INSERT INTO transactions (amount, user_id, listing_id) VALUES (${newTransaction.amount}, ${newTransaction.userId}, ${newTransaction.listingId})".update
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => newTransaction.toTransaction(id))
      .transact(transactor)
  }

  override def update(transaction: Transaction): F[Option[Transaction]] = {
    sql"UPDATE transactions SET amount = ${transaction.amount}, user_id = ${transaction.userId}, listing_id = ${transaction.listingId} WHERE id = ${transaction.id}".update.run
      .map {
        case 1 => Some(transaction)
        case _ => None
      }
      .transact(transactor)
  }

  override def delete(id: Long): F[Boolean] = {
    sql"DELETE FROM transactions WHERE id = $id".update.run
      .map(_ > 0)
      .transact(transactor)
  }
}

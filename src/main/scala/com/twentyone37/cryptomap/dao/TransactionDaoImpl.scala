package com.twentyone37.cryptomap.dao

import cats.effect.IO
import doobie._
import doobie.implicits._
import com.twentyone37.cryptomap.models.Transaction

class TransactionDaoImpl(transactor: Transactor[IO]) extends TransactionDao {
  override def get(id: Long): IO[Option[Transaction]] = {
    sql"SELECT id, amount, user_id, listing_id FROM transactions WHERE id = $id"
      .query[Transaction]
      .option
      .transact(transactor)
  }

  override def list(): IO[List[Transaction]] = {
    sql"SELECT id, amount, user_id, listing_id FROM transactions"
      .query[Transaction]
      .to[List]
      .transact(transactor)
  }

  override def create(transaction: Transaction): IO[Transaction] = {
    sql"INSERT INTO transactions (amount, user_id, listing_id) VALUES (${transaction.amount}, ${transaction.userId}, ${transaction.listingId})".update
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => transaction.copy(id = id))
      .transact(transactor)
  }

  override def update(transaction: Transaction): IO[Option[Transaction]] = {
    sql"UPDATE transactions SET amount = ${transaction.amount}, user_id = ${transaction.userId}, listing_id = ${transaction.listingId} WHERE id = ${transaction.id}".update.run
      .map {
        case 1 => Some(transaction)
        case _ => None
      }
      .transact(transactor)
  }

  override def delete(id: Long): IO[Boolean] = {
    sql"DELETE FROM transactions WHERE id = $id".update.run
      .map(_ > 0)
      .transact(transactor)
  }
}

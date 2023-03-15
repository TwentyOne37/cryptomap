package com.twentyone37.cryptomap.domain.repository

import cats.effect.kernel.{Async, Resource}
import cats.syntax.all._
import com.twentyone37.cryptomap.domain.model.entity.Transaction
import doobie._
import doobie.implicits._

class TransactionRepository[F[_]: Async](transactor: Resource[F, Transactor[F]])
    extends Repository[F, Transaction] {

  override def getById(id: Long): F[Option[Transaction]] =
    transactor.use { xa =>
      sql"""SELECT id, buyer_id, seller_id, amount FROM transactions WHERE id = $id"""
        .query[Transaction]
        .option
        .transact(xa)
    }

  override def getAll: F[List[Transaction]] =
    transactor.use { xa =>
      sql"""SELECT id, buyer_id, seller_id, amount FROM transactions"""
        .query[Transaction]
        .to[List]
        .transact(xa)
    }

  override def add(transaction: Transaction): F[Unit] =
    transactor.use { xa =>
      sql"""INSERT INTO transactions (buyer_id, seller_id, amount) VALUES (${transaction.buyerId}, ${transaction.sellerId}, ${transaction.amount})""".update.run
        .transact(xa)
        .void
    }

  override def update(transaction: Transaction): F[Unit] =
    transactor.use { xa =>
      sql"""UPDATE transactions SET buyer_id = ${transaction.buyerId}, seller_id = ${transaction.sellerId}, amount = ${transaction.amount} WHERE id = ${transaction.id}""".update.run
        .transact(xa)
        .void
    }

  override def delete(transaction: Transaction): F[Unit] =
    transactor.use { xa =>
      sql"""DELETE FROM transactions WHERE id = ${transaction.id}""".update.run
        .transact(xa)
        .void
    }
}

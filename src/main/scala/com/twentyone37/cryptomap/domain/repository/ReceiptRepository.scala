package com.twentyone37.cryptomap.domain.repository

import cats.effect.kernel.{Async, Resource}
import cats.syntax.all._
import doobie._
import doobie.implicits._

import com.twentyone37.cryptomap.domain.model.entity.Receipt

class ReceiptRepository[F[_]: Async](transactor: Resource[F, Transactor[F]])
    extends Repository[F, Receipt] {

  override def getById(id: Long): F[Option[Receipt]] =
    transactor.use { xa =>
      sql"""SELECT id, transaction_id, timestamp FROM receipts WHERE id = $id"""
        .query[Receipt]
        .option
        .transact(xa)
    }

  override def getAll: F[List[Receipt]] =
    transactor.use { xa =>
      sql"""SELECT id, transaction_id, timestamp FROM receipts"""
        .query[Receipt]
        .to[List]
        .transact(xa)
    }

  override def add(receipt: Receipt): F[Unit] =
    transactor.use { xa =>
      sql"""INSERT INTO receipts (transaction_id, timestamp) VALUES (${receipt.transactionId}, ${receipt.timestamp})""".update.run
        .transact(xa)
        .void
    }

  override def update(receipt: Receipt): F[Unit] =
    transactor.use { xa =>
      sql"""UPDATE receipts SET transaction_id = ${receipt.transactionId}, timestamp = ${receipt.timestamp} WHERE id = ${receipt.id}""".update.run
        .transact(xa)
        .void
    }

  override def delete(receipt: Receipt): F[Unit] =
    transactor.use { xa =>
      sql"""DELETE FROM receipts WHERE id = ${receipt.id}""".update.run
        .transact(xa)
        .void
    }

}

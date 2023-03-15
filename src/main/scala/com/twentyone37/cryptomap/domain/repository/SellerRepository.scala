package com.twentyone37.cryptomap.domain.repository

import cats.effect.kernel.{Async, Resource}
import cats.syntax.all._
import doobie._
import doobie.implicits._

import com.twentyone37.cryptomap.domain.model.entity.Seller

class SellerRepository[F[_]: Async](transactor: Resource[F, Transactor[F]])
    extends Repository[F, Seller] {

  override def getById(id: Long): F[Option[Seller]] =
    transactor.use { xa =>
      sql"""SELECT id, name, email FROM sellers WHERE id = $id"""
        .query[Seller]
        .option
        .transact(xa)
    }

  override def getAll: F[List[Seller]] =
    transactor.use { xa =>
      sql"""SELECT id, name, email FROM sellers"""
        .query[Seller]
        .to[List]
        .transact(xa)
    }

  override def add(seller: Seller): F[Unit] =
    transactor.use { xa =>
      sql"""INSERT INTO sellers (name, email) VALUES (${seller.name}, ${seller.email})""".update.run
        .transact(xa)
        .void
    }

  override def update(seller: Seller): F[Unit] =
    transactor.use { xa =>
      sql"""UPDATE sellers SET name = ${seller.name}, email = ${seller.email} WHERE id = ${seller.id}""".update.run
        .transact(xa)
        .void
    }

  override def delete(seller: Seller): F[Unit] =
    transactor.use { xa =>
      sql"""DELETE FROM sellers WHERE id = ${seller.id}""".update.run
        .transact(xa)
        .void
    }
}

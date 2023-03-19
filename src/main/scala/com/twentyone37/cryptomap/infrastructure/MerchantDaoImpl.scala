package com.twentyone37.cryptomap.infrastructure

import cats.effect.Sync
import doobie._
import doobie.implicits._
import com.twentyone37.cryptomap.domain.merchant.Merchant

class MerchantDaoImpl[F[_]: Sync](transactor: Transactor[F])
    extends MerchantDao[F] {
  override def get(id: Long): F[Option[Merchant]] = {
    sql"SELECT id, name, email, location FROM merchants WHERE id = $id"
      .query[Merchant]
      .option
      .transact(transactor)
  }

  override def list(): F[List[Merchant]] = {
    sql"SELECT id, name, email, location FROM merchants"
      .query[Merchant]
      .to[List]
      .transact(transactor)
  }

  override def create(merchant: Merchant): F[Merchant] = {
    sql"INSERT INTO merchants (name, email, location) VALUES (${merchant.name}, ${merchant.email}, ${merchant.location})".update
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => merchant.copy(id = id))
      .transact(transactor)
  }

  override def update(merchant: Merchant): F[Option[Merchant]] = {
    sql"UPDATE merchants SET name = ${merchant.name}, email = ${merchant.email}, location = ${merchant.location} WHERE id = ${merchant.id}".update.run
      .map {
        case 1 => Some(merchant)
        case _ => None
      }
      .transact(transactor)
  }

  override def delete(id: Long): F[Boolean] = {
    sql"DELETE FROM merchants WHERE id = $id".update.run
      .map(_ > 0)
      .transact(transactor)
  }
}

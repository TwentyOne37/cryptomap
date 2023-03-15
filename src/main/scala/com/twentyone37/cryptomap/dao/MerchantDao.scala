package com.twentyone37.cryptomap.dao

import cats.effect.IO
import doobie._
import doobie.implicits._
import com.twentyone37.cryptomap.models.Merchant

class MerchantDao(transactor: Transactor[IO]) {
  def get(id: Long): IO[Option[Merchant]] = {
    sql"SELECT id, name, email, location FROM merchants WHERE id = $id"
      .query[Merchant]
      .option
      .transact(transactor)
  }

  def list(): IO[List[Merchant]] = {
    sql"SELECT id, name, email, location FROM merchants"
      .query[Merchant]
      .to[List]
      .transact(transactor)
  }

  def create(merchant: Merchant): IO[Merchant] = {
    sql"INSERT INTO merchants (name, email, location) VALUES (${merchant.name}, ${merchant.email}, ${merchant.location})".update
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => merchant.copy(id = id))
      .transact(transactor)
  }

  def update(merchant: Merchant): IO[Option[Merchant]] = {
    sql"UPDATE merchants SET name = ${merchant.name}, email = ${merchant.email}, location = ${merchant.location} WHERE id = ${merchant.id}".update.run
      .map {
        case 1 => Some(merchant)
        case _ => None
      }
      .transact(transactor)
  }

  def delete(id: Long): IO[Boolean] = {
    sql"DELETE FROM merchants WHERE id = $id".update.run
      .map(_ > 0)
      .transact(transactor)
  }
}

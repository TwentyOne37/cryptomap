package com.twentyone37.cryptomap.infrastructure

import cats.effect.Sync
import doobie._
import doobie.implicits._
import com.twentyone37.cryptomap.domain.merchant.Merchant

class MerchantDaoImpl[F[_]: Sync](transactor: Transactor[F])
    extends MerchantDao[F] {

  override def get(id: Long): F[Option[Merchant]] =
    getQuery(id).option.transact(transactor)

  override def list(): F[List[Merchant]] =
    listQuery.to[List].transact(transactor)

  override def create(merchant: Merchant): F[Merchant] =
    createUpdate(merchant)
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => merchant.copy(id = id))
      .transact(transactor)

  override def update(merchant: Merchant): F[Option[Merchant]] =
    updateUpdate(merchant).run
      .map {
        case 1 => Some(merchant)
        case _ => None
      }
      .transact(transactor)

  override def delete(id: Long): F[Boolean] =
    deleteUpdate(id).run
      .map(_ > 0)
      .transact(transactor)

  private def getQuery(id: Long): Query0[Merchant] =
    sql"SELECT id, name, email, location FROM merchants WHERE id = $id"
      .query[Merchant]

  private val listQuery: Query0[Merchant] =
    sql"SELECT id, name, email, location FROM merchants"
      .query[Merchant]

  private def createUpdate(merchant: Merchant): Update0 =
    sql"INSERT INTO merchants (name, email, location) VALUES (${merchant.name}, ${merchant.email}, ${merchant.location})".update

  private def updateUpdate(merchant: Merchant): Update0 =
    sql"UPDATE merchants SET name = ${merchant.name}, email = ${merchant.email}, location = ${merchant.location} WHERE id = ${merchant.id}".update

  private def deleteUpdate(id: Long): Update0 =
    sql"DELETE FROM merchants WHERE id = $id".update
}

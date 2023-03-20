package com.twentyone37.cryptomap.infrastructure

import cats.effect.Sync
import doobie._
import doobie.implicits._
import com.twentyone37.cryptomap.domain.merchant.{Merchant, NewMerchant}
import com.twentyone37.cryptomap.models.Location

class MerchantDaoImpl[F[_]: Sync](transactor: Transactor[F])
    extends MerchantDao[F] {
  override def get(id: Long): F[Option[Merchant]] = {
    sql"""SELECT m.id, m.name, m.email, l.id as location_id, l.address, l.latitude, l.longitude
        FROM merchants m
        JOIN locations l ON m.location_id = l.id
        WHERE m.id = $id"""
      .query[
        (Long, String, String, Long, String, Double, Double)
      ]
      .option
      .map(_.map {
        case (
              merchantId,
              name,
              email,
              locationId,
              address,
              latitude,
              longitude
            ) =>
          Merchant(
            merchantId,
            name,
            email,
            Location(locationId, address, latitude, longitude)
          )
      })
      .transact(transactor)
  }

  override def list(): F[List[Merchant]] = {
    sql"""SELECT m.id, m.name, m.email, l.id as location_id, l.address, l.latitude, l.longitude
          FROM merchants m
          JOIN locations l ON m.location_id = l.id"""
      .query[(Long, String, String, Long, String, Double, Double)]
      .map {
        case (
              merchantId,
              name,
              email,
              locationId,
              address,
              latitude,
              longitude
            ) =>
          Merchant(
            merchantId,
            name,
            email,
            Location(locationId, address, latitude, longitude)
          )
      }
      .to[List]
      .transact(transactor)
  }

  import cats.implicits._

  override def create(merchant: NewMerchant): F[Merchant] = {
    for {
      locationId <-
        sql"INSERT INTO locations (address, latitude, longitude) VALUES (${merchant.location.address}, ${merchant.location.latitude}, ${merchant.location.longitude})".update
          .withUniqueGeneratedKeys[Long]("id")
          .transact(transactor)
      merchantId <-
        sql"INSERT INTO merchants (name, email, location_id) VALUES (${merchant.name}, ${merchant.email}, $locationId)".update
          .withUniqueGeneratedKeys[Long]("id")
          .transact(transactor)
    } yield Merchant(
      merchantId,
      merchant.name,
      merchant.email,
      merchant.location.copy(id = locationId)
    )
  }

  override def update(merchant: Merchant): F[Option[Merchant]] = {
    for {
      locationUpdateCount <-
        sql"UPDATE locations SET address = ${merchant.location.address}, latitude = ${merchant.location.latitude}, longitude = ${merchant.location.longitude} WHERE id = ${merchant.location.id}".update.run
          .transact(transactor)
      merchantUpdateCount <-
        sql"UPDATE merchants SET name = ${merchant.name}, email = ${merchant.email} WHERE id = ${merchant.id}".update.run
          .transact(transactor)
    } yield (locationUpdateCount, merchantUpdateCount) match {
      case (1, 1) => Some(merchant)
      case _      => None
    }
  }

  override def delete(id: Long): F[Boolean] = {
    for {
      locationIdOpt <- sql"SELECT location_id FROM merchants WHERE id = $id"
        .query[Long]
        .option
        .transact(transactor)
      merchantDeletedCount <-
        sql"DELETE FROM merchants WHERE id = $id".update.run
          .transact(transactor)
      _ <- locationIdOpt match {
        case Some(locationId) =>
          sql"DELETE FROM locations WHERE id = $locationId".update.run
            .transact(transactor)
        case None => ().pure[F]
      }
    } yield merchantDeletedCount > 0
  }

}

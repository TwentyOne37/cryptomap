package com.twentyone37.cryptomap.dao

import com.twentyone37.cryptomap.models.Listing

import cats.effect.IO
import doobie._
import doobie.implicits._

class ListingDao(transactor: Transactor[IO]) {
  def get(id: Long): IO[Option[Listing]] = {
    sql"SELECT id, title, description, price, merchant_id FROM listings WHERE id = $id"
      .query[Listing]
      .option
      .transact(transactor)
  }

  def list(): IO[List[Listing]] = {
    sql"SELECT id, title, description, price, merchant_id FROM listings"
      .query[Listing]
      .to[List]
      .transact(transactor)
  }

  def create(listing: Listing): IO[Listing] = {
    sql"INSERT INTO listings (title, description, price, merchant_id) VALUES (${listing.title}, ${listing.description}, ${listing.price}, ${listing.merchantId})".update
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => listing.copy(id = id))
      .transact(transactor)
  }

  def update(listing: Listing): IO[Option[Listing]] = {
    sql"UPDATE listings SET title = ${listing.title}, description = ${listing.description}, price = ${listing.price}, merchant_id = ${listing.merchantId} WHERE id = ${listing.id}".update.run
      .map {
        case 1 => Some(listing)
        case _ => None
      }
      .transact(transactor)
  }

  def delete(id: Long): IO[Boolean] = {
    sql"DELETE FROM listings WHERE id = $id".update.run
      .map(_ > 0)
      .transact(transactor)
  }
}

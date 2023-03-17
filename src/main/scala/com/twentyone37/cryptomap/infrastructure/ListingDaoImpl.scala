package com.twentyone37.cryptomap.infrastructure

import com.twentyone37.cryptomap.models.Listing
import com.twentyone37.cryptomap.models.currencies.Crypto
import cats.effect.IO
import doobie._
import doobie.implicits._
import doobie.util.query.Query0
import doobie.util.update.Update0

import com.twentyone37.cryptomap.dao.ListingDao

class ListingDaoImpl(transactor: Transactor[IO]) extends ListingDao {

  implicit val cryptoMeta: Meta[Crypto] =
    Meta[String].timap[Crypto](Crypto.fromString)(_.toString)

  override def get(id: Long): IO[Option[Listing]] =
    getQuery(id).option.transact(transactor)

  override def list(): IO[List[Listing]] =
    listQuery.to[List].transact(transactor)

  override def create(listing: Listing): IO[Listing] =
    createUpdate(listing)
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => listing.copy(id = id))
      .transact(transactor)

  override def update(listing: Listing): IO[Option[Listing]] =
    updateUpdate(listing).run
      .map {
        case 1 => Some(listing)
        case _ => None
      }
      .transact(transactor)

  override def delete(id: Long): IO[Boolean] =
    deleteUpdate(id).run
      .map(_ > 0)
      .transact(transactor)

  private def getQuery(id: Long): Query0[Listing] =
    sql"""
    SELECT id, title, description, price, currency, merchant_id FROM listings
    WHERE id = $id""".query[Listing]

  private val listQuery: Query0[Listing] =
    sql"""
    SELECT id, title, description, price, currency, merchant_id FROM listings"""
      .query[Listing]

  private def createUpdate(listing: Listing): Update0 =
    sql"""
       INSERT INTO listings (title, description, price, currency, merchant_id)
       VALUES (${listing.title}, ${listing.description}, ${listing.price}, ${listing.currency.toString}, ${listing.merchantId})
     """.update

  private def updateUpdate(listing: Listing): Update0 =
    sql"""
       UPDATE listings SET title = ${listing.title}, description = ${listing.description}, price = ${listing.price}, currency = ${listing.currency.toString}, merchant_id = ${listing.merchantId}
       WHERE id = ${listing.id}
     """.update

  private def deleteUpdate(id: Long): Update0 =
    sql"""
    DELETE FROM listings WHERE id = $id
  """.update
}

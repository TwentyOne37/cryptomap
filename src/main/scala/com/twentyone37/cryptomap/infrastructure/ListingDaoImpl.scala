package com.twentyone37.cryptomap.infrastructure

import cats.effect.Sync
import doobie._
import doobie.implicits._
import doobie.implicits.javatimedrivernative._
import doobie.util.Get
import com.twentyone37.cryptomap.models.currencies.Crypto
import com.twentyone37.cryptomap.domain.listing._
import com.twentyone37.cryptomap.infrastructure.ListingDao

class ListingDaoImpl[F[_]: Sync](transactor: Transactor[F])
    extends ListingDao[F] {

  implicit val cryptoGet: Get[Crypto] =
    Get[String].map(Crypto.fromString)

  implicit val cryptoMeta: Meta[Crypto] =
    Meta[String].timap[Crypto](Crypto.fromString)(_.toString)

  override def get(id: Long): F[Option[Listing]] =
    getQuery(id).option.transact(transactor)

  override def list(): F[List[Listing]] =
    listQuery.to[List].transact(transactor)

  override def create(listing: Listing): F[Listing] =
    createUpdate(listing)
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => listing.copy(id = id))
      .transact(transactor)

  override def update(listing: Listing): F[Option[Listing]] =
    updateUpdate(listing).run
      .map {
        case 1 => Some(listing)
        case _ => None
      }
      .transact(transactor)

  override def delete(id: Long): F[Boolean] =
    deleteUpdate(id).run
      .map(_ > 0)
      .transact(transactor)

  private def getQuery(id: Long): Query0[Listing] =
    sql"""
    SELECT id, title, description, price, currency, created_at, updated_at FROM listings
    WHERE id = $id""".query[Listing]

  private val listQuery: Query0[Listing] =
    sql"""
    SELECT id, title, description, price, currency, created_at, updated_at FROM listings"""
      .query[Listing]

  private def createUpdate(listing: Listing): Update0 =
    sql"""
       INSERT INTO listings (title, description, price, currency, created_at, updated_at)
       VALUES (${listing.title}, ${listing.description}, ${listing.price}, ${listing.crypto.toString}, ${listing.createdAt}, ${listing.updatedAt})
     """.update

  private def updateUpdate(listing: Listing): Update0 =
    sql"""
       UPDATE listings SET title = ${listing.title}, description = ${listing.description}, price = ${listing.price}, currency = ${listing.crypto.toString}, updated_at = ${listing.updatedAt}
       WHERE id = ${listing.id}
     """.update

  private def deleteUpdate(id: Long): Update0 =
    sql"""
    DELETE FROM listings WHERE id = $id
  """.update
}

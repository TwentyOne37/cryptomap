package com.twentyone37.cryptomap.infrastructure

import cats.effect.Sync
import doobie._
import doobie.implicits._
import doobie.implicits.javatimedrivernative._
import com.twentyone37.cryptomap.models.currencies.Crypto
import com.twentyone37.cryptomap.domain.listing._
import com.twentyone37.cryptomap.infrastructure.ListingDao
import cats.Applicative

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

  override def create(newListing: NewListing): F[Listing] =
    createUpdate(newListing)
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => newListing.toListing(id))
      .transact(transactor)

  override def update(listing: Listing): F[Option[Listing]] =
    updateUpdate(listing).run
      .map {
        case 1 => Some(listing)
        case _ => None
      }
      .transact(transactor)

  override def delete(id: Long): F[Boolean] = {
    val hasAssociatedTransactions: ConnectionIO[Boolean] = sql"""
    SELECT EXISTS (SELECT 1 FROM transactions WHERE listing_id = $id)
  """.query[Boolean].unique

    hasAssociatedTransactions
      .flatMap { exists =>
        if (exists) {
          Applicative[ConnectionIO].pure(false)
        } else {
          deleteUpdate(id).run.map(_ > 0)
        }
      }
      .transact(transactor)
  }

  private def getQuery(id: Long): Query0[Listing] =
    sql"""
    SELECT id, title, description, price, crypto, created_at, updated_at FROM listings
    WHERE id = $id""".query[Listing]

  private val listQuery: Query0[Listing] =
    sql"""
    SELECT id, title, description, price, crypto, created_at, updated_at FROM listings"""
      .query[Listing]

  private def createUpdate(newListing: NewListing): Update0 = {
    val now = java.time.LocalDateTime.now()
    sql"""
     INSERT INTO listings (title, description, price, crypto, created_at, updated_at)
     VALUES (${newListing.title}, ${newListing.description}, ${newListing.price}, ${newListing.crypto.toString}, $now, $now)
   """.update
  }

  private def updateUpdate(listing: Listing): Update0 =
    sql"""
       UPDATE listings SET title = ${listing.title}, description = ${listing.description}, price = ${listing.price}, crypto = ${listing.crypto.toString}, updated_at = ${listing.updatedAt}
       WHERE id = ${listing.id}
     """.update

  private def deleteUpdate(id: Long): Update0 =
    sql"""
    DELETE FROM listings WHERE id = $id
  """.update
}

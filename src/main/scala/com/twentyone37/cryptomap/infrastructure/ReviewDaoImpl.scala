package com.twentyone37.cryptomap.infrastructure

import cats.effect.Sync
import doobie._
import doobie.implicits._
import com.twentyone37.cryptomap.domain.review.Review

class ReviewDaoImpl[F[_]: Sync](transactor: Transactor[F])
    extends ReviewDao[F] {
  override def get(id: Long): F[Option[Review]] = {
    sql"SELECT id, rating, comment, user_id, merchant_id FROM reviews WHERE id = $id"
      .query[Review]
      .option
      .transact(transactor)
  }

  override def list(): F[List[Review]] = {
    sql"SELECT id, rating, comment, user_id, merchant_id FROM reviews"
      .query[Review]
      .to[List]
      .transact(transactor)
  }

  override def create(review: Review): F[Review] = {
    sql"INSERT INTO reviews (rating, comment, user_id, merchant_id) VALUES (${review.rating}, ${review.comment}, ${review.userId}, ${review.merchantId})".update
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => review.copy(id = id))
      .transact(transactor)
  }

  override def update(review: Review): F[Option[Review]] = {
    sql"UPDATE reviews SET rating = ${review.rating}, comment = ${review.comment}, user_id = ${review.userId}, merchant_id = ${review.merchantId} WHERE id = ${review.id}".update.run
      .map {
        case 1 => Some(review)
        case _ => None
      }
      .transact(transactor)
  }

  override def delete(id: Long): F[Boolean] = {
    sql"DELETE FROM reviews WHERE id = $id".update.run
      .map(_ > 0)
      .transact(transactor)
  }
}

package com.twentyone37.cryptomap.dao

import cats.effect.IO
import doobie._
import doobie.implicits._
import com.twentyone37.cryptomap.models.Review

class ReviewDaoImpl(transactor: Transactor[IO]) extends ReviewDao {
  override def get(id: Long): IO[Option[Review]] = {
    sql"SELECT id, rating, comment, user_id, merchant_id FROM reviews WHERE id = $id"
      .query[Review]
      .option
      .transact(transactor)
  }

  override def list(): IO[List[Review]] = {
    sql"SELECT id, rating, comment, user_id, merchant_id FROM reviews"
      .query[Review]
      .to[List]
      .transact(transactor)
  }

  override def create(review: Review): IO[Review] = {
    sql"INSERT INTO reviews (rating, comment, user_id, merchant_id) VALUES (${review.rating}, ${review.comment}, ${review.userId}, ${review.merchantId})".update
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => review.copy(id = id))
      .transact(transactor)
  }

  override def update(review: Review): IO[Option[Review]] = {
    sql"UPDATE reviews SET rating = ${review.rating}, comment = ${review.comment}, user_id = ${review.userId}, merchant_id = ${review.merchantId} WHERE id = ${review.id}".update.run
      .map {
        case 1 => Some(review)
        case _ => None
      }
      .transact(transactor)
  }

  override def delete(id: Long): IO[Boolean] = {
    sql"DELETE FROM reviews WHERE id = $id".update.run
      .map(_ > 0)
      .transact(transactor)
  }
}

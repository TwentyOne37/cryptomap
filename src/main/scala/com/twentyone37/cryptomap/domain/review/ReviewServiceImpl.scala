package com.twentyone37.cryptomap.domain.review

import com.twentyone37.cryptomap.infrastructure.ReviewDao

class ReviewServiceImpl[F[_]](reviewDao: ReviewDao[F])
    extends ReviewService[F] {
  override def get(id: Long): F[Option[Review]] = reviewDao.get(id)
  override def list(): F[List[Review]] = reviewDao.list()
  override def create(review: Review): F[Review] = reviewDao.create(review)
  override def update(review: Review): F[Option[Review]] =
    reviewDao.update(review)
  override def delete(id: Long): F[Boolean] = reviewDao.delete(id)
}

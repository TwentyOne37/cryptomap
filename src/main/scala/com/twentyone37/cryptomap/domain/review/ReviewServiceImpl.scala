package com.twentyone37.cryptomap.domain.review

import cats.effect.IO
import com.twentyone37.cryptomap.infrastructure.ReviewDao

class ReviewServiceImpl(reviewDao: ReviewDao) {
  def get(id: Long): IO[Option[Review]] = reviewDao.get(id)
  def list(): IO[List[Review]] = reviewDao.list()
  def create(review: Review): IO[Review] = reviewDao.create(review)
  def update(review: Review): IO[Option[Review]] = reviewDao.update(review)
  def delete(id: Long): IO[Boolean] = reviewDao.delete(id)
}

package com.twentyone37.cryptomap.services

import cats.effect.IO
import com.twentyone37.cryptomap.models.Review
import com.twentyone37.cryptomap.dao.ReviewDao

class ReviewService(reviewDao: ReviewDao) {
  def get(id: Long): IO[Option[Review]] = reviewDao.get(id)
  def list(): IO[List[Review]] = reviewDao.list()
  def create(review: Review): IO[Review] = reviewDao.create(review)
  def update(review: Review): IO[Option[Review]] = reviewDao.update(review)
  def delete(id: Long): IO[Boolean] = reviewDao.delete(id)
}

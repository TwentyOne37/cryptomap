package com.twentyone37.cryptomap.services

import com.twentyone37.cryptomap.models.Review

trait ReviewService[F[_]] {
  def getReviews: F[List[Review]]
  def getReview(id: Long): F[Option[Review]]
  def createReview(review: Review): F[Review]
  def updateReview(id: Long, review: Review): F[Option[Review]]
  def deleteReview(id: Long): F[Option[Unit]]
}

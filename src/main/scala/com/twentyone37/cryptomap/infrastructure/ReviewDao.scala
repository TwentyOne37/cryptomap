package com.twentyone37.cryptomap.infrastructure

import com.twentyone37.cryptomap.domain.review.{NewReview, Review}

trait ReviewDao[F[_]] {
  def get(id: Long): F[Option[Review]]
  def list(): F[List[Review]]
  def create(review: NewReview): F[Review]
  def update(review: Review): F[Option[Review]]
  def delete(id: Long): F[Boolean]
}

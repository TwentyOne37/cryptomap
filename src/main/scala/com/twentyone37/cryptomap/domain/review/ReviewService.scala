package com.twentyone37.cryptomap.domain.review

trait ReviewService[F[_]] {
  def get(id: Long): F[Option[Review]]
  def list(): F[List[Review]]
  def create(review: NewReview): F[Review]
  def update(review: Review): F[Option[Review]]
  def delete(id: Long): F[Boolean]
}

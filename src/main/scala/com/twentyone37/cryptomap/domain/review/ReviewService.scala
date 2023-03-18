package com.twentyone37.cryptomap.domain.review

import cats.effect.IO

trait ReviewService {
  def get(id: Long): IO[Option[Review]]
  def list(): IO[List[Review]]
  def create(review: Review): IO[Review]
  def update(review: Review): IO[Option[Review]]
  def delete(id: Long): IO[Boolean]
}

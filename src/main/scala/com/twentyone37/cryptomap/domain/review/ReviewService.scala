package com.twentyone37.cryptomap.services

import cats.effect.IO
import com.twentyone37.cryptomap.models.Review

trait ReviewService {
  def get(id: Long): IO[Option[Review]]
  def list(): IO[List[Review]]
  def create(review: Review): IO[Review]
  def update(review: Review): IO[Option[Review]]
  def delete(id: Long): IO[Boolean]
}

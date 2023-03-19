package com.twentyone37.cryptomap.infrastructure

import com.twentyone37.cryptomap.models.Location

trait LocationDao[F[_]] {
  def get(id: Long): F[Option[Location]]
  def create(location: Location): F[Option[Location]]
}

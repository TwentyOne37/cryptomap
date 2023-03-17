package com.twentyone37.cryptomap.infrastructure

import cats.effect.IO
import com.twentyone37.cryptomap.models.Location

trait LocationDao {
  def get(id: Long): IO[Option[Location]]
  def create(location: Location): IO[Location]
}

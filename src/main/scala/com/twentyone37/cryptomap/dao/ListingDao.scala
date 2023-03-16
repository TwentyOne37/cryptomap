package com.twentyone37.cryptomap.dao

import cats.effect.IO
import com.twentyone37.cryptomap.models.Listing

trait ListingDao {
  def get(id: Long): IO[Option[Listing]]
  def list(): IO[List[Listing]]
  def create(listing: Listing): IO[Listing]
  def update(listing: Listing): IO[Option[Listing]]
  def delete(id: Long): IO[Boolean]
}

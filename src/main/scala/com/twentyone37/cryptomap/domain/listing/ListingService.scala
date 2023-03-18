package com.twentyone37.cryptomap.domain.listing

import com.twentyone37.cryptomap.domain.listing.Listing

trait ListingService[F[_]] {
  def list(): F[List[Listing]]
  def get(id: Long): F[Option[Listing]]
  def create(listing: Listing): F[Listing]
  def update(listing: Listing): F[Option[Listing]]
  def delete(id: Long): F[Boolean]
}

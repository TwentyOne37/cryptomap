package com.twentyone37.cryptomap.infrastructure

import com.twentyone37.cryptomap.domain.listing.{Listing, NewListing}

trait ListingDao[F[_]] {
  def get(id: Long): F[Option[Listing]]
  def list(): F[List[Listing]]
  def create(newListing: NewListing): F[Listing]
  def update(listing: Listing): F[Option[Listing]]
  def delete(id: Long): F[Boolean]
}

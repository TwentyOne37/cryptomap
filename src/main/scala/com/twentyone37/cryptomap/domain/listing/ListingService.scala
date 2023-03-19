package com.twentyone37.cryptomap.domain.listing

trait ListingService[F[_]] {
  def list(): F[List[Listing]]
  def get(id: Long): F[Option[Listing]]
  def create(newListing: NewListing): F[Listing]
  def update(listing: Listing): F[Option[Listing]]
  def delete(id: Long): F[Boolean]
}

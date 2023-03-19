package com.twentyone37.cryptomap.domain.listing

import com.twentyone37.cryptomap.infrastructure.ListingDao

class ListingServiceImpl[F[_]](listingDao: ListingDao[F])
    extends ListingService[F] {
  override def get(id: Long): F[Option[Listing]] = listingDao.get(id)
  override def list(): F[List[Listing]] = listingDao.list()
  override def create(newListing: NewListing): F[Listing] =
    listingDao.create(newListing)
  override def update(listing: Listing): F[Option[Listing]] =
    listingDao.update(listing)
  override def delete(id: Long): F[Boolean] = listingDao.delete(id)
}

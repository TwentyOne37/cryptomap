package com.twentyone37.cryptomap.services

import cats.effect.IO
import com.twentyone37.cryptomap.models.Listing
import com.twentyone37.cryptomap.dao.ListingDao

class ListingServiceImpl(listingDao: ListingDao) extends ListingService {
  override def get(id: Long): IO[Option[Listing]] = listingDao.get(id)
  override def list(): IO[List[Listing]] = listingDao.list()
  override def create(listing: Listing): IO[Listing] =
    listingDao.create(listing)
  override def update(listing: Listing): IO[Option[Listing]] =
    listingDao.update(listing)
  override def delete(id: Long): IO[Boolean] = listingDao.delete(id)
}

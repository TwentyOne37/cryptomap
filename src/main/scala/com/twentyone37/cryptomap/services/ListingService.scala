package com.twentyone37.cryptomap.services

import cats.effect.IO
import com.twentyone37.cryptomap.models.Listing
import com.twentyone37.cryptomap.dao.ListingDao

class ListingService(listingDao: ListingDao) {
  def get(id: Long): IO[Option[Listing]] = listingDao.get(id)
  def list(): IO[List[Listing]] = listingDao.list()
  def create(listing: Listing): IO[Listing] = listingDao.create(listing)
  def update(listing: Listing): IO[Option[Listing]] = listingDao.update(listing)
  def delete(id: Long): IO[Boolean] = listingDao.delete(id)
}

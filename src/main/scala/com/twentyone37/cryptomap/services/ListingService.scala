package com.twentyone37.cryptomap.services

import com.twentyone37.cryptomap.models.Listing

trait ListingService[F[_]] {
  def getListings: F[List[Listing]]
  def getListing(id: Long): F[Option[Listing]]
  def createListing(listing: Listing): F[Listing]
  def updateListing(id: Long, listing: Listing): F[Option[Listing]]
  def deleteListing(id: Long): F[Option[Unit]]
}

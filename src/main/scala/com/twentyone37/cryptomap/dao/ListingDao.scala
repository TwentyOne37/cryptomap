package com.twentyone37.cryptomap.dao

import com.twentyone37.cryptomap.models.Listing

trait ListingDao {
  def get(id: Long): Option[Listing]
  def list(): List[Listing]
  def create(listing: Listing): Listing
  def update(listing: Listing): Option[Listing]
  def delete(id: Long): Boolean
}

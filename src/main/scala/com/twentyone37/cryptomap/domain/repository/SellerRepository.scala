package com.twentyone37.cryptomap.domain.repository

import com.twentyone37.cryptomap.domain.model.entity.Seller

object SellerRepository extends Repository[Seller] {
  private var sellers: Map[Long, Seller] = Map()

  def getById(id: Long): Option[Seller] = sellers.get(id)
  def getAll: List[Seller] = sellers.values.toList
  def add(seller: Seller): Unit = sellers += (seller.id -> seller)
  def update(seller: Seller): Unit = sellers += (seller.id -> seller)
  def delete(seller: Seller): Unit = sellers -= seller.id
}

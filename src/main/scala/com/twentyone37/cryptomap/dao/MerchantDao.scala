package com.twentyone37.cryptomap.dao

import com.twentyone37.cryptomap.models.Merchant

trait MerchantDao {
  def get(id: Long): Option[Merchant]
  def list(): List[Merchant]
  def create(merchant: Merchant): Merchant
  def update(merchant: Merchant): Option[Merchant]
  def delete(id: Long): Boolean
}

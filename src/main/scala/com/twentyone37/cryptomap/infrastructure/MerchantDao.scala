package com.twentyone37.cryptomap.infrastructure

import com.twentyone37.cryptomap.domain.merchant.{Merchant, NewMerchant}

trait MerchantDao[F[_]] {
  def get(id: Long): F[Option[Merchant]]
  def list(): F[List[Merchant]]
  def create(newMerchant: NewMerchant): F[Merchant]
  def update(merchant: Merchant): F[Option[Merchant]]
  def delete(id: Long): F[Boolean]
}

package com.twentyone37.cryptomap.domain.merchant

trait MerchantService[F[_]] {
  def get(id: Long): F[Option[Merchant]]
  def list(): F[List[Merchant]]
  def create(merchant: NewMerchant): F[Merchant]
  def update(merchant: Merchant): F[Option[Merchant]]
  def delete(id: Long): F[Boolean]
}

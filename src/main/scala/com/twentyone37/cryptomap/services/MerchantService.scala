package com.twentyone37.cryptomap.services

import com.twentyone37.cryptomap.models.Merchant

trait MerchantService[F[_]] {
  def getMerchants: F[List[Merchant]]
  def getMerchant(id: Long): F[Option[Merchant]]
  def createMerchant(merchant: Merchant): F[Merchant]
  def updateMerchant(id: Long, merchant: Merchant): F[Option[Merchant]]
  def deleteMerchant(id: Long): F[Option[Unit]]
}

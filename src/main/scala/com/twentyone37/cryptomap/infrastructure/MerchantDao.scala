package com.twentyone37.cryptomap.infrastructure

import cats.effect.IO
import com.twentyone37.cryptomap.domain.merchant.Merchant

trait MerchantDao {
  def get(id: Long): IO[Option[Merchant]]
  def list(): IO[List[Merchant]]
  def create(merchant: Merchant): IO[Merchant]
  def update(merchant: Merchant): IO[Option[Merchant]]
  def delete(id: Long): IO[Boolean]
}

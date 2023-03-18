package com.twentyone37.cryptomap.domain.merchant

import cats.effect.IO

trait MerchantService {
  def get(id: Long): IO[Option[Merchant]]
  def list(): IO[List[Merchant]]
  def create(merchant: Merchant): IO[Merchant]
  def update(merchant: Merchant): IO[Option[Merchant]]
  def delete(id: Long): IO[Boolean]
}

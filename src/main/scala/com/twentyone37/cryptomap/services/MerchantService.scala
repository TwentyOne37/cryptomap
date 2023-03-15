package com.twentyone37.cryptomap.services

import cats.effect.IO
import com.twentyone37.cryptomap.models.Merchant
import com.twentyone37.cryptomap.dao.MerchantDao

class MerchantService(merchantDao: MerchantDao) {
  def get(id: Long): IO[Option[Merchant]] = merchantDao.get(id)
  def list(): IO[List[Merchant]] = merchantDao.list()
  def create(merchant: Merchant): IO[Merchant] = merchantDao.create(merchant)
  def update(merchant: Merchant): IO[Option[Merchant]] =
    merchantDao.update(merchant)
  def delete(id: Long): IO[Boolean] = merchantDao.delete(id)
}

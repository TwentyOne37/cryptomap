package com.twentyone37.cryptomap.services

import cats.effect.IO
import com.twentyone37.cryptomap.models.Merchant
import com.twentyone37.cryptomap.dao.MerchantDao

class MerchantServiceImpl(merchantDao: MerchantDao) extends MerchantService {
  override def get(id: Long): IO[Option[Merchant]] = merchantDao.get(id)
  override def list(): IO[List[Merchant]] = merchantDao.list()
  override def create(merchant: Merchant): IO[Merchant] =
    merchantDao.create(merchant)
  override def update(merchant: Merchant): IO[Option[Merchant]] =
    merchantDao.update(merchant)
  override def delete(id: Long): IO[Boolean] = merchantDao.delete(id)
}
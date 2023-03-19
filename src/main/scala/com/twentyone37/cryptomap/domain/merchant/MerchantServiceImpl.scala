package com.twentyone37.cryptomap.domain.merchant

import com.twentyone37.cryptomap.infrastructure.MerchantDao

class MerchantServiceImpl[F[_]](merchantDao: MerchantDao[F])
    extends MerchantService[F] {
  override def get(id: Long): F[Option[Merchant]] = merchantDao.get(id)
  override def list(): F[List[Merchant]] = merchantDao.list()
  override def create(merchant: Merchant): F[Merchant] =
    merchantDao.create(merchant)
  override def update(merchant: Merchant): F[Option[Merchant]] =
    merchantDao.update(merchant)
  override def delete(id: Long): F[Boolean] = merchantDao.delete(id)
}

package com.twentyone37.cryptomap.domain.merchant

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.twentyone37.cryptomap.domain.DomainTestSuite
import com.twentyone37.cryptomap.domain.merchant.Merchant
import com.twentyone37.cryptomap.models.Location
import com.twentyone37.cryptomap.infrastructure.MerchantDao
import org.mockito.Mockito._

object MerchantServiceSpec extends DomainTestSuite {

  val merchantDao = mock[MerchantDao]

  val sampleLocation: Location =
    Location(address = "Sample Address", latitude = 1, longitude = 1)

  val sampleMerchant = Merchant(
    id = 1L,
    name = "Sample Merchant",
    email = "sample@merchant.com",
    location = sampleLocation
  )

  when(merchantDao.get(1L)).thenReturn(IO.pure(Some(sampleMerchant)))
  when(merchantDao.list()).thenReturn(IO.pure(List(sampleMerchant)))
  when(merchantDao.create(sampleMerchant)).thenReturn(IO.pure(sampleMerchant))
  when(merchantDao.update(sampleMerchant))
    .thenReturn(IO.pure(Some(sampleMerchant)))
  when(merchantDao.delete(1L)).thenReturn(IO.pure(true))

  val merchantService = new MerchantServiceImpl(merchantDao)

  pureTest("get(id)") {
    expect(merchantService.get(1L).unsafeRunSync() == Some(sampleMerchant))
  }

  pureTest("list()") {
    expect(merchantService.list().unsafeRunSync() == List(sampleMerchant))
  }

  pureTest("create(merchant)") {
    expect(
      merchantService.create(sampleMerchant).unsafeRunSync() == sampleMerchant
    )
  }

  pureTest("update(merchant)") {
    expect(
      merchantService.update(sampleMerchant).unsafeRunSync() == Some(
        sampleMerchant
      )
    )
  }

  pureTest("delete(id)") {
    expect(merchantService.delete(1L).unsafeRunSync() == true)
  }
}

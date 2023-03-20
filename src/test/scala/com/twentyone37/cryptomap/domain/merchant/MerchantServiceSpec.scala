package com.twentyone37.cryptomap.domain.merchant

import cats.effect.IO
import weaver.SimpleIOSuite
import com.twentyone37.cryptomap.domain.merchant.Merchant
import com.twentyone37.cryptomap.models.Location
import com.twentyone37.cryptomap.infrastructure.MerchantDao

object MerchantServiceSpec extends SimpleIOSuite {

  val sampleLocation: Location =
    Location(1, address = "Sample Address", latitude = 1, longitude = 1)

  val sampleMerchant = Merchant(
    id = 1L,
    name = "Sample Merchant",
    email = "sample@merchant.com",
    location = sampleLocation
  )

  val newSampleMerchat = NewMerchant(
    name = "Sample Merchant",
    email = "sample@merchant.com",
    location = sampleLocation
  )

  val merchantDao = new MerchantDao[IO] {
    def get(id: Long): IO[Option[Merchant]] =
      IO.pure(if (id == sampleMerchant.id) Some(sampleMerchant) else None)
    def list(): IO[List[Merchant]] = IO.pure(List(sampleMerchant))
    def create(merchant: NewMerchant): IO[Merchant] = IO.pure(sampleMerchant)
    def update(merchant: Merchant): IO[Option[Merchant]] =
      IO.pure(Some(sampleMerchant))
    def delete(id: Long): IO[Boolean] = IO.pure(id == sampleMerchant.id)
  }

  val merchantService = new MerchantServiceImpl(merchantDao)

  test("get(id)") {
    for {
      result <- merchantService.get(1L)
    } yield expect(result == Some(sampleMerchant))
  }

  test("list()") {
    for {
      result <- merchantService.list()
    } yield expect(result == List(sampleMerchant))
  }

  test("create(merchant)") {
    for {
      result <- merchantService.create(newSampleMerchat)
    } yield expect(result == sampleMerchant)
  }

  test("update(merchant)") {
    for {
      result <- merchantService.update(sampleMerchant)
    } yield expect(result == Some(sampleMerchant))
  }

  test("delete(id)") {
    for {
      result <- merchantService.delete(1L)
    } yield expect(result == true)
  }

}

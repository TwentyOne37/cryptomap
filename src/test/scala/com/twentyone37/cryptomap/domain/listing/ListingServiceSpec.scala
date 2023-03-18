package com.twentyone37.cryptomap.domain.listing

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.twentyone37.cryptomap.models.currencies.Crypto
import org.mockito.Mockito._
import com.twentyone37.cryptomap.domain.DomainTestSuite
import com.twentyone37.cryptomap.infrastructure.ListingDao

import java.math.BigDecimal

object ListingServiceSpec extends DomainTestSuite {

  val listingDao = mock[ListingDao]
  val sampleListing = Listing(
    id = 1L,
    title = "Sample Listing",
    description = Some("A sample listing"),
    price = new BigDecimal(10),
    currency = Crypto.fromString("BTC"),
    merchantId = 1L
  )

  // Mock implementation for get(id) method
  when(listingDao.get(1L)).thenReturn(IO.pure(Some(sampleListing)))

// Mock implementation for list() method
  when(listingDao.list()).thenReturn(IO.pure(List(sampleListing)))

// Mock implementation for create(listing) method
  when(listingDao.create(sampleListing)).thenReturn(IO.pure(sampleListing))

// Mock implementation for update(listing) method
  when(listingDao.update(sampleListing))
    .thenReturn(IO.pure(Some(sampleListing)))

// Mock implementation for delete(id) method
  when(listingDao.delete(1L)).thenReturn(IO.pure(true))

  val listingService = new ListingServiceImpl(listingDao)

  pureTest("get(id)") {
    expect(listingService.get(1L).unsafeRunSync() == Some(sampleListing))
  }

  pureTest("list()") {
    expect(listingService.list().unsafeRunSync() == List(sampleListing))
  }

  pureTest("create(listing)") {
    expect(
      listingService.create(sampleListing).unsafeRunSync() == sampleListing
    )
  }

  pureTest("update(listing)") {
    expect(
      listingService.update(sampleListing).unsafeRunSync() == Some(
        sampleListing
      )
    )
  }

  pureTest("delete(id)") {
    expect(listingService.delete(1L).unsafeRunSync() == true)
  }
}

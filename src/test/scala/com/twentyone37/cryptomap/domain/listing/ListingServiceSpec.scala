package com.twentyone37.cryptomap.domain.listing

import cats.effect.IO
import com.twentyone37.cryptomap.models.currencies.Crypto
import com.twentyone37.cryptomap.infrastructure.ListingDao
import weaver.SimpleIOSuite

import java.math.BigDecimal
import java.time.LocalDateTime

object ListingServiceSpec extends SimpleIOSuite {

  val now = LocalDateTime.now()
  val sampleListing = Listing(
    id = 1L,
    title = "Sample Listing",
    description = "A sample listing",
    price = new BigDecimal(10),
    crypto = Crypto.fromString("BTC"),
    createdAt = now,
    updatedAt = now
  )
  val sampleNewListing = NewListing(
    title = "Sample Listing",
    description = "A sample listing",
    price = 1L,
    crypto = Crypto.fromString("BTC")
  )

  val listingDao: ListingDao[IO] = new ListingDao[IO] {
    def get(id: Long): IO[Option[Listing]] =
      IO.pure(if (id == sampleListing.id) Some(sampleListing) else None)
    def list(): IO[List[Listing]] = IO.pure(List(sampleListing))
    def create(listing: NewListing): IO[Listing] = IO.pure(sampleListing)
    def update(listing: Listing): IO[Option[Listing]] =
      IO.pure(Some(sampleListing))
    def delete(id: Long): IO[Boolean] = IO.pure(id == sampleListing.id)
  }

  val listingService = new ListingServiceImpl(listingDao)

  test("get(id)") {
    for {
      result <- listingService.get(1L)
    } yield expect(result == Some(sampleListing))
  }

  test("list()") {
    for {
      result <- listingService.list()
    } yield expect(result == List(sampleListing))
  }

  test("create(listing)") {
    for {
      result <- listingService.create(sampleNewListing)
    } yield expect(result == sampleListing)
  }

  test("update(listing)") {
    for {
      result <- listingService.update(sampleListing)
    } yield expect(result == Some(sampleListing))
  }

  test("delete(id)") {
    for {
      result <- listingService.delete(1L)
    } yield expect(result == true)
  }
}

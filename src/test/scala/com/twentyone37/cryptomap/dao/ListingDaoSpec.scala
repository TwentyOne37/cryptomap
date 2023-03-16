package com.twentyone37.cryptomap.dao

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.twentyone37.cryptomap.models.Listing
import doobie._
import doobie.implicits._
import doobie.scalatest.IOChecker
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import scala.concurrent.duration._
import com.twentyone37.cryptomap.models.currencies.BTC

import java.math.BigDecimal

import com.twentyone37.cryptomap.dao.ListingDao
class ListingDaoSpec
    extends AnyFunSuite
    with Matchers
    with BeforeAndAfterAll
    with IOChecker {

  val transactor: Transactor[IO] =
    Transactor.fromDriverManager[IO](
      "org.h2.Driver",
      "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
      "sa",
      ""
    )

  val timeout = 1.second

  val listingDao = new ListingDao(transactor)

  override def beforeAll(): Unit = {
    sql"""
    CREATE TABLE listings (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    description VARCHAR,
    price NUMERIC NOT NULL,
    currency VARCHAR NOT NULL,
    merchant_id BIGINT NOT NULL)""".update.run
      .transact(transactor)
      .unsafeRunTimed(timeout) match {
      case Some(_) => ()
      case None    => fail("Failed to create listings table")
    }
  }

  override def afterAll(): Unit = {
    sql"DROP TABLE listings".update.run
      .transact(transactor)
      .unsafeRunTimed(timeout) match {
      case None    => fail("Failed to drop listings table")
      case Some(_) => ()
    }
  }

  test("get query") {
    check(listingDao.getQuery(42L))
  }

  test("list query") {
    check(listingDao.listQuery)
  }

  test("create update") {
    val listing = Listing(
      id = 0L,
      title = "Test Listing",
      description = Some("Test Description"),
      price = new BigDecimal(10),
      currency = BTC,
      merchantId = 1L
    )
    check(listingDao.createUpdate(listing))
  }

  test("update update") {
    val listing = Listing(
      id = 42L,
      title = "Test Listing",
      description = Some("Test Description"),
      price = new BigDecimal(10),
      currency = BTC,
      merchantId = 1L
    )
    check(listingDao.updateUpdate(listing))
  }

  test("delete update") {
    check(listingDao.deleteUpdate(42L))
  }

}

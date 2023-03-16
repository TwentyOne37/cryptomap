package com.twentyone37.cryptomap.dao

import cats.syntax.all._
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.twentyone37.cryptomap.dao.UserDao
import com.twentyone37.cryptomap.models.User
import doobie._
import doobie.implicits._
import doobie.scalatest.IOChecker
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import scala.concurrent.duration._

class UserDaoSpec
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

  val userDao = new UserDao(transactor)

  override def beforeAll(): Unit = {
    sql"""
    CREATE TABLE users (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      username VARCHAR NOT NULL UNIQUE,
      password VARCHAR NOT NULL
    )
  """.update.run.transact(transactor).unsafeRunTimed(timeout) match {
      case Some(_) => ()
      case None    => fail("Failed to create users table")
    }
  }

  override def afterAll(): Unit = {
    sql"DROP TABLE users".update.run
      .transact(transactor)
      .unsafeRunTimed(timeout) match {
      case None    => fail("Failed to drop users table")
      case Some(_) => ()
    }
  }

  test("UserDao.findUserByUsername should typecheck") {
    check(userDao.findUserByUsernameQuery("username"))
  }

  test("UserDao.createUser should typecheck") {
    check(userDao.createUserQuery(User(0, "username", "password")))
  }
}

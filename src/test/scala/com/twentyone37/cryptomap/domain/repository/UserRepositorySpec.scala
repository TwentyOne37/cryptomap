package com.twentyone37.cryptomap.domain.repository

import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import doobie.implicits._

class UserRepositorySpec extends AnyFlatSpec with Matchers with BeforeAndAfter {
  val transactor = RepositoryTestUtils
    .createTranasctorResource[IO](
      "jdbc:postgresql://localhost:5432/cryptomap",
      "postgres",
      "postgres"
    )

  val userRepository = new UserRepository[IO](transactor)

  before {
    transactor
      .use { xa =>
        sql"""CREATE TABLE IF NOT EXISTS users (
          id SERIAL PRIMARY KEY,
          name VARCHAR(255) NOT NULL,
          email VARCHAR(255) NOT NULL,
          created_at TIMESTAMP NOT NULL DEFAULT NOW(),
          updated_at TIMESTAMP NOT NULL DEFAULT NOW());
          """.update.run.transact(xa)
      }
      .unsafeRunSync()
  }

  after {
    transactor
      .use { xa =>
        sql"""DROP TABLE IF EXISTS users;""".update.run.transact(xa)
      }
      .unsafeRunSync()
  }

  "getById" should "return None if the user does not exists" in {
    val result = userRepository.getById(999).unsafeRunSync()
    result shouldBe None
  }

}

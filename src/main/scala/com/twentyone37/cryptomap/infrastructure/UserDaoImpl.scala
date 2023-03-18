package com.twentyone37.cryptomap.infrastructure

import cats.effect.IO
import doobie._
import doobie.implicits._
import com.twentyone37.cryptomap.domain.user.User
import com.twentyone37.cryptomap.infrastructure.UserDao

class UserDaoImpl(transactor: Transactor[IO]) extends UserDao {
  override def get(id: Long): IO[Option[User]] = {
    sql"SELECT id, name, email, password FROM users WHERE id = $id"
      .query[User]
      .option
      .transact(transactor)
  }

  override def findByEmail(email: String): IO[Option[User]] = {
    sql"SELECT id, name, email, password FROM users WHERE email = $email"
      .query[User]
      .option
      .transact(transactor)
  }

  override def create(user: User): IO[User] = {
    sql"INSERT INTO users (name, email, password) VALUES (${user.username}, ${user.email}, ${user.password})".update
      .withUniqueGeneratedKeys[Long]("id")
      .map(id => user.copy(id = id))
      .transact(transactor)
  }

  override def update(user: User): IO[Option[User]] = {
    sql"UPDATE users SET name = ${user.username}, email = ${user.email}, password = ${user.password} WHERE id = ${user.id}".update.run
      .map {
        case 1 => Some(user)
        case _ => None
      }
      .transact(transactor)
  }

  override def delete(id: Long): IO[Boolean] = {
    sql"DELETE FROM users WHERE id = $id".update.run
      .map(_ > 0)
      .transact(transactor)
  }
}

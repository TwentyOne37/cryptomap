package com.twentyone37.cryptomap.dao

import cats.effect.IO
import com.twentyone37.cryptomap.models.User
import doobie._
import doobie.implicits._

class UserDao(transactor: Transactor[IO]) {

  def findUserByUsernameQuery(username: String): Query0[User] = {
    sql"""SELECT id, username, password FROM users WHERE username = $username"""
      .query[User]
  }

  def findUserByUsername(username: String): IO[Option[User]] = {
    findUserByUsernameQuery(username).option.transact(transactor)
  }

  def createUserQuery(user: User): Update0 = {
    sql"""INSERT INTO users (username, password) VALUES (${user.username}, ${user.password})""".update
  }

  def createUser(user: User): IO[User] = {
    createUserQuery(user)
      .withUniqueGeneratedKeys[Int]("id")
      .transact(transactor)
      .map(id => user.copy(id = id.toLong))
  }

}

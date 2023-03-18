package com.twentyone37.cryptomap.infrastructure

import cats.effect.IO
import com.twentyone37.cryptomap.domain.user.User

trait UserDao {
  def get(id: Long): IO[Option[User]]
  def findByEmail(email: String): IO[Option[User]]
  def create(user: User): IO[User]
  def update(user: User): IO[Option[User]]
  def delete(id: Long): IO[Boolean]
}

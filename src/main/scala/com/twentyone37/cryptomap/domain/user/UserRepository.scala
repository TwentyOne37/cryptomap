package com.twentyone37.cryptomap.repository

import cats.effect.Sync
import cats.syntax.functor._
import com.twentyone37.cryptomap.models.User

trait UserRepository[F[_]] {
  def findByUsername(username: String): F[Option[User]]
  def create(user: User): F[Unit]
}

object UserRepository {
  def apply[F[_]](implicit ev: UserRepository[F]): UserRepository[F] = ev

  def impl[F[_]: Sync]: UserRepository[F] = new UserRepository[F] {
    private val storage = new scala.collection.concurrent.TrieMap[String, User]

    override def findByUsername(username: String): F[Option[User]] = {
      Sync[F].delay(storage.get(username))
    }

    override def create(user: User): F[Unit] = {
      Sync[F].delay(storage.put(user.username, user)).void
    }
  }
}

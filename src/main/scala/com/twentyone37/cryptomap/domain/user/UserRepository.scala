package com.twentyone37.cryptomap.domain.user

import cats.effect.Sync

import com.twentyone37.cryptomap.domain.user.User
trait UserRepository[F[_]] {
  def findByUsername(username: String): F[Option[User]]
  def create(user: User): F[User]
}

object UserRepository {
  def apply[F[_]](implicit ev: UserRepository[F]): UserRepository[F] = ev

  def impl[F[_]: Sync]: UserRepository[F] = new UserRepository[F] {
    private val storage = new scala.collection.concurrent.TrieMap[String, User]

    override def findByUsername(username: String): F[Option[User]] = {
      Sync[F].delay(storage.get(username))
    }

    override def create(user: User): F[User] = {
      Sync[F].delay {
        storage.put(user.username, user)
        user
      }
    }
  }
}

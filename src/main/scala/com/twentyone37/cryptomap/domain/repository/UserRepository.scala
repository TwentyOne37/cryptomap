package com.twentyone37.cryptomap.domain.repository

import cats.effect.kernel.{Async, Resource}
import cats.syntax.all._
import doobie._
import doobie.implicits._

import com.twentyone37.cryptomap.domain.model.entity.User

class UserRepository[F[_]: Async](transactor: Resource[F, Transactor[F]])
    extends Repository[F, User] {

  override def getById(id: Long): F[Option[User]] =
    transactor.use { xa =>
      sql"""SELECT id, name, email FROM users WHERE id = $id"""
        .query[User]
        .option
        .transact(xa)
    }

  override def getAll: F[List[User]] =
    transactor.use { xa =>
      sql"""SELECT id, name, email FROM users"""
        .query[User]
        .to[List]
        .transact(xa)
    }

  override def add(user: User): F[Unit] =
    transactor.use { xa =>
      sql"""INSERT INTO users (name, email) VALUES (${user.name}, ${user.email})""".update.run
        .transact(xa)
        .void
    }

  override def update(user: User): F[Unit] =
    transactor.use { xa =>
      sql"""UPDATE users SET name = ${user.name}, email = ${user.email} WHERE id = ${user.id}""".update.run
        .transact(xa)
        .void
    }

  override def delete(user: User): F[Unit] =
    transactor.use { xa =>
      sql"""DELETE FROM users WHERE id = ${user.id}""".update.run
        .transact(xa)
        .void
    }
}

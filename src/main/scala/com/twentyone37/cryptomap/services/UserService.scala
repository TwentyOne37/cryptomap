package com.twentyone37.cryptomap.services

import cats.effect.Sync
import cats.effect.Async
import cats.syntax.all._
import com.twentyone37.cryptomap.models._
import com.twentyone37.cryptomap.repository.UserRepository
import com.twentyone37.cryptomap.auth.Auth

trait UserService[F[_]] {
  def login(username: String, password: String): F[Option[User]]
  def register(username: String, password: String): F[User]
}

object UserService {
  def apply[F[_]: Sync: Async](userRepo: UserRepository[F]): UserService[F] =
    new UserService[F] {

      def login(username: String, password: String): F[Option[User]] = {
        userRepo.findByUsername(username).flatMap {
          case Some(user) =>
            Async[F].delay(Auth.checkPassword(password, user.password)).map {
              case true  => Some(user)
              case false => None
            }
          case None => Sync[F].pure(None)
        }
      }

      def register(username: String, password: String): F[User] = {
        for {
          hashedPassword <- Async[F].delay(Auth.hashPassword(password))
          _ <- userRepo.create(User(0, username, hashedPassword))
          user <- userRepo.findByUsername(username)
        } yield user.get
      }
    }
}

package com.twentyone37.cryptomap.services

import cats.effect.Async
import cats.syntax.all._
import com.twentyone37.cryptomap.models._
import com.twentyone37.cryptomap.repository.UserRepository
import com.twentyone37.cryptomap.auth.Auth

trait UserService[F[_]] {
  def login(username: String, password: String): F[Option[User]]
  def register(username: String, password: String, email: String): F[User]
}

object UserService {
  def apply[F[_]: Async](userRepo: UserRepository[F]): UserService[F] =
    new UserService[F] {

      def login(username: String, password: String): F[Option[User]] = {
        userRepo.findByUsername(username).flatMap {
          case Some(user) =>
            Async[F]
              .fromEither(Auth.checkPasswordEither(password, user.password))
              .map { isValid =>
                if (isValid) Some(user) else None
              }
          case None => Async[F].pure(None)
        }
      }

      def register(
          username: String,
          password: String,
          email: String
      ): F[User] = {
        Async[F].fromEither(Auth.hashPasswordEither(password)).flatMap {
          hashedPassword =>
            userRepo.create(User(0, username, hashedPassword, email)).flatMap {
              _ =>
                userRepo.findByUsername(username).map(_.get)
            }
        }
      }
    }
}

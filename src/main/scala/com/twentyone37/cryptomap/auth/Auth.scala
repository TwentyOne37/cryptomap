package com.twentyone37.cryptomap.auth

import cats.effect.IO
import com.github.t3hnar.bcrypt._
import scala.annotation.nowarn

object Auth {
  def hashPassword(password: String): IO[String] = {
    IO(password.boundedBcrypt)
  }

  // todo: remove this once we have a better solution
  @nowarn("cat=deprecation")
  def checkPassword(password: String, hash: String): IO[Boolean] = {
    IO(password.isBcrypted(hash))
  }
}

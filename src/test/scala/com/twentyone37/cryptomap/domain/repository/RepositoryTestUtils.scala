package com.twentyone37.cryptomap.domain.repository

import cats.effect.{Async, Resource}
import doobie.Transactor
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts

object RepositoryTestUtils {
  def createTranasctorResource[F[_]: Async](
      dbUrl: String,
      dbUser: String,
      dbPassword: String
  ): Resource[F, Transactor[F]] = for {
    connectEC <- ExecutionContexts.fixedThreadPool[F](32)
    transactor <- HikariTransactor.newHikariTransactor[F](
      "org.postgresql.Driver",
      dbUrl,
      dbUser,
      dbPassword,
      connectEC
    )
  } yield transactor
}

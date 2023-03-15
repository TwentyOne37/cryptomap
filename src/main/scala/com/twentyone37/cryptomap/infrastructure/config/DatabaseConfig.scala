package com.twentyone37.cryptomap.infrastructure.config

import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import cats.effect._

case class DatabaseConfig(
    driver: String,
    url: String,
    user: String,
    password: String
)

object DatabaseConfig {

  def transactor[F[_]: Async](
      dbConfig: DatabaseConfig
  ): Resource[F, HikariTransactor[F]] = {

    val connectEC = ExecutionContexts.synchronous

    HikariTransactor.newHikariTransactor[F](
      dbConfig.driver,
      dbConfig.url,
      dbConfig.user,
      dbConfig.password,
      connectEC
    )
  }
}

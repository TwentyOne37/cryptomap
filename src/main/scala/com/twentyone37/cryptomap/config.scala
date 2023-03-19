package com.twentyone37.cryptomap

import cats.effect.{Async, Resource}
import com.zaxxer.hikari.HikariConfig
import doobie.hikari.HikariTransactor
import org.flywaydb.core.Flyway
import cats.effect.std.Dispatcher
import scala.concurrent.ExecutionContext

case class AppConfig(server: ServerConfig, database: DatabaseConfig)

case class ServerConfig(host: String, port: Int)

case class DatabaseConfig(
    driver: String,
    url: String,
    user: String,
    password: String,
    poolSize: Int
)

object DatabaseConfig {
  def dbTransactor[F[_]: Async](
      db: DatabaseConfig
  ): Resource[F, HikariTransactor[F]] = {
    val hikariConfig = new HikariConfig()
    hikariConfig.setDriverClassName(db.driver)
    hikariConfig.setJdbcUrl(db.url)
    hikariConfig.setUsername(db.user)
    hikariConfig.setPassword(db.password)
    hikariConfig.setMaximumPoolSize(db.poolSize)

    Dispatcher[F].flatMap { _ =>
      implicit val ec: ExecutionContext = ExecutionContext.global
      HikariTransactor.newHikariTransactor[F](
        db.driver,
        db.url,
        db.user,
        db.password,
        ec
      )
    }
  }

  def runMigrations[F[_]: Async](db: DatabaseConfig): F[Unit] = Async[F].delay {
    val flyway = Flyway
      .configure()
      .dataSource(db.url, db.user, db.password)
      .baselineOnMigrate(true)
      .load()
    flyway.migrate()
    ()
  }
}

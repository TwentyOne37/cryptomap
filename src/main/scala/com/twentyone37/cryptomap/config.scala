package com.twentyone37.cryptomap

import cats.effect.{Async, Resource}
import com.zaxxer.hikari.HikariConfig
import doobie.hikari.HikariTransactor
import org.flywaydb.core.Flyway
import cats.effect.std.Dispatcher
import scala.concurrent.ExecutionContext
import java.sql.DriverManager
import java.util.Properties
import java.sql.ResultSet

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

  def createDatabaseIfNotExists[F[_]: Async](db: DatabaseConfig): F[Unit] =
    Async[F].delay {
      Class.forName("org.postgresql.Driver")
      val connectionUrl =
        db.url
          .split("/")
          .dropRight(1)
          .mkString("/") + "/" // Add a "/" at the end of the URL
      val dbName = db.url.split("/").last // Extract the database name
      val props = new Properties()
      props.setProperty("user", db.user)
      props.setProperty("password", db.password)

      val connection = DriverManager.getConnection(connectionUrl, props)
      val statement = connection.createStatement()

      val resultSet: ResultSet = statement.executeQuery(
        s"SELECT 1 FROM pg_database WHERE datname = '$dbName'"
      )
      if (!resultSet.next()) {
        statement.execute(s"CREATE DATABASE $dbName")
      }

      resultSet.close()
      statement.close()
      connection.close()
      ()
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

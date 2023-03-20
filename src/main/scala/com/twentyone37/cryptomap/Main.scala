package com.twentyone37.cryptomap

import cats.effect._
import cats.implicits._
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import org.http4s.implicits._
import org.slf4j.LoggerFactory
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import com.twentyone37.cryptomap.application.services._
import com.twentyone37.cryptomap.infrastructure._
import com.twentyone37.cryptomap.domain.listing._
import com.twentyone37.cryptomap.domain.merchant._
import com.twentyone37.cryptomap.domain.review._
import com.twentyone37.cryptomap.domain.transaction._

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val appConfig = ConfigSource.default.loadOrThrow[AppConfig]
    val xa =
      DatabaseConfig.dbTransactor[IO](appConfig.database).use { transactor =>
        for {
          _ <- DatabaseConfig.createDatabaseIfNotExists[IO](appConfig.database)
          _ <- DatabaseConfig.runMigrations[IO](appConfig.database)
          logger = LoggerFactory.getLogger(getClass)
          userDao = new UserDaoImpl(transactor)
          listingDao = new ListingDaoImpl(transactor)
          listingService = new ListingServiceImpl(listingDao)
          merchantDao = new MerchantDaoImpl(transactor)
          merchantService = new MerchantServiceImpl(merchantDao)
          reviewDao = new ReviewDaoImpl(transactor)
          reviewService = new ReviewServiceImpl(reviewDao)
          transactionDao = new TransactionDaoImpl(transactor)
          transactionService = new TransactionServiceImpl(transactionDao)
          routes =
            ListingRoutes.routes(listingService, logger)(
              Async[IO]
            ) <+> MerchantRoutes.routes(merchantService)(
              Async[IO]
            ) <+> ReviewRoutes.routes(
              reviewService
            )(Async[IO]) <+> TransactionRoutes.routes(transactionService)(
              Async[IO]
            )
          httpApp = Router("/" -> routes).orNotFound
          exitCode <- BlazeServerBuilder[IO]
            .bindHttp(appConfig.server.port, appConfig.server.host)
            .withHttpApp(httpApp)
            .serve
            .compile
            .drain
            .as(ExitCode.Success)
        } yield exitCode
      }
    xa
  }
}

package com.twentyone37.cryptomap

import cats.effect._
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
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
          _ <- DatabaseConfig.runMigrations[IO](appConfig.database)
          userDao = new UserDaoImpl(transactor)
          listingDao = new ListingDaoImpl(transactor)
          listingService = new ListingServiceImpl(listingDao)
          merchantDao = new MerchantDaoImpl(transactor)
          merchantService = new MerchantServiceImpl(merchantDao)
          reviewDao = new ReviewDaoImpl(transactor)
          reviewService = new ReviewServiceImpl(reviewDao)
          transactionDao = new TransactionDaoImpl(transactor)
          transactionService = new TransactionServiceImpl(transactionDao)
          httpApp = Router(
            "/listings" -> ListingRoutes.routes(listingService)(Async[IO]),
            "/merchants" -> MerchantRoutes.routes(merchantService)(Async[IO]),
            "/reviews" -> ReviewRoutes.routes(reviewService)(Async[IO]),
            "/transactions" -> TransactionRoutes.routes(transactionService)(
              Async[IO]
            )
          ).orNotFound
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

package com.twentyone37.cryptomap

import cats.effect._
import org.http4s.server.Router
import org.http4s.blaze.server.BlazeServerBuilder
import com.twentyone37.cryptomap.routes._

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(
        Router(
          "/merchants" -> MerchantRoutes[IO](),
          "/listings" -> ListingRoutes[IO](),
          "/transactions" -> TransactionRoutes[IO](),
          "/reviews" -> ReviewRoutes[IO]()
        ).orNotFound
      )
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}

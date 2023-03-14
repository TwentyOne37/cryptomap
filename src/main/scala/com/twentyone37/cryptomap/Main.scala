package com.twentyone37.cryptomap

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {
  val run = CryptomapapiServer.run[IO]
}

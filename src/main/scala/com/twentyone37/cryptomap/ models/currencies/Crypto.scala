package com.twentyone37.cryptomap.models.currencies

sealed trait Crypto {
  override def toString: String = this match {
    case BTC  => "BTC"
    case ETH  => "ETH"
    case BNB  => "BNB"
    case ADA  => "ADA"
    case SOL  => "SOL"
    case XRP  => "XRP"
    case DOT  => "DOT"
    case DOGE => "DOGE"
    case LINK => "LINK"
    case LTC  => "LTC"
  }
}

case object BTC extends Crypto
case object ETH extends Crypto
case object BNB extends Crypto
case object ADA extends Crypto
case object SOL extends Crypto
case object XRP extends Crypto
case object DOT extends Crypto
case object DOGE extends Crypto
case object LINK extends Crypto
case object LTC extends Crypto

object Crypto {
  def fromString(s: String): Crypto = s match {
    case "BTC"  => BTC
    case "ETH"  => ETH
    case "BNB"  => BNB
    case "ADA"  => ADA
    case "SOL"  => SOL
    case "XRP"  => XRP
    case "DOT"  => DOT
    case "DOGE" => DOGE
    case "LINK" => LINK
    case "LTC"  => LTC
    case _      => throw new IllegalArgumentException(s"Unknown Crypto: $s")
  }
}

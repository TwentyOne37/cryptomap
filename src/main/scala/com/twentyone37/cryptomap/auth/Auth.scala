package com.twentyone37.cryptomap.auth

import java.security.MessageDigest
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import java.util.Base64
import scala.util.Try

object Auth {
  val salt = "some-salt"
  val iterations = 10000
  val keyLength = 512

  def hashPasswordEither(password: String): Either[Throwable, String] = {
    Try(hashPassword(password)).toEither
  }

  def checkPasswordEither(
      password: String,
      hashedPassword: String
  ): Either[Throwable, Boolean] = {
    Try(checkPassword(password, hashedPassword)).toEither
  }

  private def hashPassword(password: String): String = {
    val keySpec =
      new PBEKeySpec(password.toCharArray, salt.getBytes, iterations, keyLength)
    val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
    val hash = skf.generateSecret(keySpec).getEncoded
    Base64.getEncoder.encodeToString(hash)
  }

  private def checkPassword(
      password: String,
      hashedPassword: String
  ): Boolean = {
    val providedHash = Base64.getDecoder.decode(hashedPassword)
    val keySpec =
      new PBEKeySpec(password.toCharArray, salt.getBytes, iterations, keyLength)
    val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
    val calculatedHash = skf.generateSecret(keySpec).getEncoded

    MessageDigest.isEqual(providedHash, calculatedHash)
  }
}

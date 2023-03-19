package com.twentyone37.cryptomap.domain.transaction

import cats.effect.IO
import weaver.SimpleIOSuite

import com.twentyone37.cryptomap.domain.transaction.Transaction
import com.twentyone37.cryptomap.infrastructure.TransactionDao

object TransactionServiceSpec extends SimpleIOSuite {

  val sampleTransaction = Transaction(
    id = 1L,
    amount = 100.00,
    userId = 1L,
    listingId = 1L
  )

  val transactionDao: TransactionDao[IO] = new TransactionDao[IO] {
    def get(id: Long): IO[Option[Transaction]] =
      IO.pure(if (id == sampleTransaction.id) Some(sampleTransaction) else None)
    def list(): IO[List[Transaction]] = IO.pure(List(sampleTransaction))
    def create(transaction: Transaction): IO[Transaction] =
      IO.pure(sampleTransaction)
    def update(transaction: Transaction): IO[Option[Transaction]] =
      IO.pure(Some(sampleTransaction))
    def delete(id: Long): IO[Boolean] = IO.pure(id == sampleTransaction.id)
  }

  val transactionService = new TransactionServiceImpl(transactionDao)

  test("get(id)") {
    for {
      result <- transactionService.get(1L)
    } yield expect(result == Some(sampleTransaction))
  }

  test("list()") {
    for {
      result <- transactionService.list()
    } yield expect(result == List(sampleTransaction))
  }

  test("create(transaction)") {
    for {
      result <- transactionService.create(sampleTransaction)
    } yield expect(result == sampleTransaction)
  }

  test("update(transaction)") {
    for {
      result <- transactionService.update(sampleTransaction)
    } yield expect(result == Some(sampleTransaction))
  }

  test("delete(id)") {
    for {
      result <- transactionService.delete(1L)
    } yield expect(result == true)
  }

}

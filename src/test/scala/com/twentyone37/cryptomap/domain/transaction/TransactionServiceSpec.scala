package com.twentyone37.cryptomap.domain.transaction

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.twentyone37.cryptomap.domain.DomainTestSuite
import com.twentyone37.cryptomap.domain.transaction.Transaction
import com.twentyone37.cryptomap.infrastructure.TransactionDao
import org.mockito.Mockito._

object TransactionServiceSpec extends DomainTestSuite {

  val transactionDao = mock[TransactionDao]
  val sampleTransaction = Transaction(
    id = 1L,
    amount = 100.00,
    userId = 1L,
    listingId = 1L
  )

  when(transactionDao.get(1L)).thenReturn(IO.pure(Some(sampleTransaction)))
  when(transactionDao.list()).thenReturn(IO.pure(List(sampleTransaction)))
  when(transactionDao.create(sampleTransaction))
    .thenReturn(IO.pure(sampleTransaction))
  when(transactionDao.update(sampleTransaction))
    .thenReturn(IO.pure(Some(sampleTransaction)))
  when(transactionDao.delete(1L)).thenReturn(IO.pure(true))

  val transactionService = new TransactionServiceImpl(transactionDao)

  pureTest("get(id)") {
    expect(
      transactionService.get(1L).unsafeRunSync() == Some(sampleTransaction)
    )
  }

  pureTest("list()") {
    expect(transactionService.list().unsafeRunSync() == List(sampleTransaction))
  }

  pureTest("create(transaction)") {
    expect(
      transactionService
        .create(sampleTransaction)
        .unsafeRunSync() == sampleTransaction
    )
  }

  pureTest("update(transaction)") {
    expect(
      transactionService.update(sampleTransaction).unsafeRunSync() == Some(
        sampleTransaction
      )
    )
  }

  pureTest("delete(id)") {
    expect(transactionService.delete(1L).unsafeRunSync() == true)
  }
}

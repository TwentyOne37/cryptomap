package com.twentyone37.cryptomap.domain.review

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.twentyone37.cryptomap.domain.DomainTestSuite
import com.twentyone37.cryptomap.infrastructure.ReviewDao
import com.twentyone37.cryptomap.domain.review.Review
import org.mockito.Mockito._

object ReviewServiceSpec extends DomainTestSuite {

  val reviewDao = mock[ReviewDao]
  val sampleReview = Review(
    id = 1L,
    rating = 5,
    comment = "A sample review",
    userId = 5,
    merchantId = 1L
  )

  when(reviewDao.get(1L)).thenReturn(IO.pure(Some(sampleReview)))
  when(reviewDao.list()).thenReturn(IO.pure(List(sampleReview)))
  when(reviewDao.create(sampleReview)).thenReturn(IO.pure(sampleReview))
  when(reviewDao.update(sampleReview)).thenReturn(IO.pure(Some(sampleReview)))
  when(reviewDao.delete(1L)).thenReturn(IO.pure(true))

  val reviewService = new ReviewServiceImpl(reviewDao)

  pureTest("get(id)") {
    expect(reviewService.get(1L).unsafeRunSync() == Some(sampleReview))
  }

  pureTest("list()") {
    expect(reviewService.list().unsafeRunSync() == List(sampleReview))
  }

  pureTest("create(review)") {
    expect(
      reviewService.create(sampleReview).unsafeRunSync() == sampleReview
    )
  }

  pureTest("update(review)") {
    expect(
      reviewService.update(sampleReview).unsafeRunSync() == Some(sampleReview)
    )
  }

  pureTest("delete(id)") {
    expect(reviewService.delete(1L).unsafeRunSync() == true)
  }
}

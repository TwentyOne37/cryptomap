package com.twentyone37.cryptomap.domain.review

import cats.effect.IO
import com.twentyone37.cryptomap.infrastructure.ReviewDao
import weaver.SimpleIOSuite

object ReviewServiceSpec extends SimpleIOSuite {

  val sampleReview = Review(
    id = 1L,
    rating = 5,
    comment = "A sample review",
    userId = 5,
    merchantId = 1L
  )

  val reviewDao: ReviewDao[IO] = new ReviewDao[IO] {
    def get(id: Long): IO[Option[Review]] =
      IO.pure(if (id == sampleReview.id) Some(sampleReview) else None)
    def list(): IO[List[Review]] = IO.pure(List(sampleReview))
    def create(review: Review): IO[Review] = IO.pure(sampleReview)
    def update(review: Review): IO[Option[Review]] =
      IO.pure(Some(sampleReview))
    def delete(id: Long): IO[Boolean] = IO.pure(id == sampleReview.id)
  }

  val reviewService = new ReviewServiceImpl[IO](reviewDao)

  test("get(id)") {
    for {
      result <- reviewService.get(1L)
    } yield expect(result == Some(sampleReview))
  }

  test("list()") {
    for {
      result <- reviewService.list()
    } yield expect(result == List(sampleReview))
  }

  test("create(review)") {
    for {
      result <- reviewService.create(sampleReview)
    } yield expect(result == sampleReview)
  }

  test("update(review)") {
    for {
      result <- reviewService.update(sampleReview)
    } yield expect(result == Some(sampleReview))
  }

  test("delete(id)") {
    for {
      result <- reviewService.delete(1L)
    } yield expect(result == true)
  }
}

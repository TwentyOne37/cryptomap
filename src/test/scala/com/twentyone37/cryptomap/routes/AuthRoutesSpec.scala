// package com.twentyone37.cryptomap.routes

// import cats.effect._
// import io.circe.generic.auto._
// import io.circe.syntax._
// import org.http4s._
// import org.http4s.implicits._
// import org.http4s.circe._
// import org.scalatest.funsuite.AnyFunSuite
// import org.scalatest.matchers.should.Matchers
// import com.twentyone37.cryptomap.models._
// import com.twentyone37.cryptomap.services.UserService

// class AuthRoutesSpec extends AnyFunSuite with Matchers {
//   implicit val runtime: IORuntime = IORuntime.global

//   val userService: UserService[IO] = new UserService[IO] {
//     def login(username: String, password: String): IO[Option[User]] = IO(
//       None
//     ) // Stubbed method
//     def register(username: String, password: String): IO[User] = IO(
//       User(0, "test_user", "test_password")
//     ) // Stubbed method
//   }

//   val authRoutes: AuthRoutes[IO] = AuthRoutes[IO](userService)

//   test("POST /login should decode LoginRequest") {
//     val req = Request[IO](method = Method.POST, uri = uri"/login")
//       .withEntity(LoginRequest("test_user", "test_password").asJson)

//     val resp = authRoutes.routes.orNotFound.run(req)
//     resp.unsafeRunSync().status shouldEqual Status.Ok
//   }

//   test("POST /register should decode RegisterRequest") {
//     val req = Request[IO](method = Method.POST, uri = uri"/register")
//       .withEntity(RegisterRequest("test_user", "test_password").asJson)

//     val resp = authRoutes.routes.orNotFound.run(req)
//     resp.unsafeRunSync().status shouldEqual Status.Created
//   }
// }

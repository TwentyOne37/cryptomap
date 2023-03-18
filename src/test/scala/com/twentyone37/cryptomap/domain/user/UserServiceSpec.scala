// package com.twentyone37.cryptomap.domain.user

// import cats.effect.IO
// import cats.effect.unsafe.implicits.global
// import com.twentyone37.cryptomap.domain.DomainTestSuite
// import com.twentyone37.cryptomap.domain.user.User
// import org.mockito.Mockito._

// object UserServiceSpec extends DomainTestSuite {

//   val userRepository = mock[UserRepository[IO]]
//   val sampleUser = User(
//     id = 1L,
//     username = "SampleUser",
//     password = "SamplePassword",
//     email = "sample@user.com"
//   )

//   val userService = UserService[IO](userRepository)

//   when(userRepository.findByUsername("SampleUser"))
//     .thenReturn(IO.pure(Some(sampleUser)))
//   when(userRepository.create(sampleUser)).thenReturn(IO.pure(sampleUser))

//   pureTest("login()") {
//     expect(
//       userService.login("SampleUser", "SamplePassword").unsafeRunSync() == Some(
//         sampleUser
//       )
//     )
//   }

//   pureTest("register()") {
//     expect(
//       userService
//         .register("SampleUser", "SamplePassword", "sample@user.com")
//         .unsafeRunSync() == sampleUser
//     )
//   }
// }

package com.twentyone37.cryptomap.dao

import com.twentyone37.cryptomap.models.User

trait UserDao {
  def findUserByUsername(username: String): Option[User]
  def createUser(user: User): User
}

package com.twentyone37.cryptomap.domain.repository

import com.twentyone37.cryptomap.domain.model.entity.User

object UserRepository extends Repository[User] {
  private var users: Map[Long, User] = Map()

  def getById(id: Long): Option[User] = users.get(id)
  def getAll: List[User] = users.values.toList
  def add(user: User): Unit = users += (user.id -> user)
  def update(user: User): Unit = users += (user.id -> user)
  def delete(user: User): Unit = users -= user.id
}

package com.twentyone37.cryptomap.domain.repository

import com.twentyone37.cryptomap.domain.model.entity.Entity

trait Repository[T <: Entity] {
  def getById(id: Long): Option[T]
  def getAll: List[T]
  def add(entity: T): Unit
  def update(entity: T): Unit
  def delete(entity: T): Unit
}

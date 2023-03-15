package com.twentyone37.cryptomap.domain.repository

import com.twentyone37.cryptomap.domain.model.entity.Entity

trait Repository[F[_], T <: Entity] {
  def getById(id: Long): F[Option[T]]
  def getAll: F[List[T]]
  def add(entity: T): F[Unit]
  def update(entity: T): F[Unit]
  def delete(entity: T): F[Unit]
}

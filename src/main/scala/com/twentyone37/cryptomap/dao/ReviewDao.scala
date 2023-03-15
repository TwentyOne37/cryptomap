package com.twentyone37.cryptomap.dao

import com.twentyone37.cryptomap.models.Review

trait ReviewDao {
  def get(id: Long): Option[Review]
  def list(): List[Review]
  def create(review: Review): Review
  def update(review: Review): Option[Review]
  def delete(id: Long): Boolean
}

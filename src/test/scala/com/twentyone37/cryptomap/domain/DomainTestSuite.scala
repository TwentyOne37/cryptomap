package com.twentyone37.cryptomap.domain

import org.mockito.Mockito
import weaver.SimpleIOSuite

trait DomainTestSuite extends SimpleIOSuite {

  def mock[T: Manifest]: T =
    Mockito.mock(implicitly[Manifest[T]].runtimeClass.asInstanceOf[Class[T]])
}

package com.twentyone37.cryptomap.domain.merchant

import com.twentyone37.cryptomap.models.Location

case class Merchant(id: Long, name: String, email: String, location: Location)

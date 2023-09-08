package com.open.exchange.domain.models

data class ConvertModelD(
    val rates: List<ConvertRateD>,
    val timestamp: Int,
)

data class ConvertRateD(
    val currencyCode: String,
    val rate: Float
)

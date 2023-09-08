package com.open.exchange.domain.models

data class ResultModel(
    val currencyCode: String,
    val result: Float,
    val currencyName: String,
    val source: String,
    val inputValue: Float
){
    companion object{
        val DEFAULT = ResultModel(
            currencyCode = String(),
            result = 0F,
            currencyName = String(),
            source = String(),
            inputValue = 0F
        )
    }
}

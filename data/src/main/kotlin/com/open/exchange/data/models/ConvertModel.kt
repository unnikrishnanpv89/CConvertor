package com.open.exchange.data.models

import com.google.gson.JsonObject

data class ConvertModel(
    val base: String?,
    val disclaimer: String?,
    val license: String?,
    val rates: JsonObject?,
    val timestamp: Int?,
)
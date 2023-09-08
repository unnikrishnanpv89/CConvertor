package com.open.exchange.data.remote

import com.google.gson.JsonObject
import com.open.exchange.data.models.ConvertModel
import com.open.exchange.domain.models.common.Response
import kotlinx.coroutines.flow.Flow

interface ExchangeDataSource {

    suspend fun getCurrencyList(): Flow<Response<JsonObject>>

    suspend fun getConvertList(): Flow<Response<ConvertModel>>
}
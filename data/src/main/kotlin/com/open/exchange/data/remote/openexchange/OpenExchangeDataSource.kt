package com.open.exchange.data.remote.openexchange

import com.google.gson.JsonObject
import com.open.exchange.data.models.ConvertModel
import com.open.exchange.data.remote.ExchangeDataSource
import com.open.exchange.data.remote.networkCall
import com.open.exchange.data.remote.openexchange.OpenExchangeAPIConstants.APP_ID
import com.open.exchange.domain.models.common.Response
import kotlinx.coroutines.flow.Flow

class OpenExchangeDataSource(private val apiService: APIService): ExchangeDataSource {

    override suspend fun getCurrencyList(): Flow<Response<JsonObject>> =
         networkCall {
            apiService.getCurrencyList()
        }

    override suspend fun getConvertList(): Flow<Response<ConvertModel>> =
        networkCall {
            apiService.getConvertList(appId = APP_ID)
        }
}
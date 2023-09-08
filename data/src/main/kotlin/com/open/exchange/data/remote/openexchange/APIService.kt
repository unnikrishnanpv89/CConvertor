package com.open.exchange.data.remote.openexchange

import com.google.gson.JsonObject
import com.open.exchange.data.models.ConvertModel
import com.open.exchange.data.remote.openexchange.OpenExchangeAPIConstants.APPID
import com.open.exchange.data.remote.openexchange.OpenExchangeAPIConstants.CONVERT_LIST
import com.open.exchange.data.remote.openexchange.OpenExchangeAPIConstants.CURRENCY_LIST
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET(CURRENCY_LIST)
    suspend fun getCurrencyList(): JsonObject

    @GET(CONVERT_LIST)
    suspend fun getConvertList(
        @Query(APPID) appId: String
        /** not supported in free account **/
        //@Query(BASE) baseCurrency: String
    ): ConvertModel
}
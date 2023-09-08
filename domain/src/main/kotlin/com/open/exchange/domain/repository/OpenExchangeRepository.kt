package com.open.exchange.domain.repository

import com.open.exchange.domain.models.common.Response
import kotlinx.coroutines.flow.Flow

interface OpenExchangeRepository {
    suspend fun getCurrencyList(): Flow<Response<Boolean>>

    suspend fun getConvertList(): Flow<Response<Boolean>>
}
package com.open.exchange.domain.usecase.currency

import com.open.exchange.domain.models.common.Response
import com.open.exchange.domain.repository.OpenExchangeRepository
import kotlinx.coroutines.flow.Flow

class GetCurrencyListUseCase(private val openExchangeRepository: OpenExchangeRepository) {
    suspend fun execute(): Flow<Response<Boolean>> =
        openExchangeRepository.getCurrencyList()
}
package com.open.exchange.domain.usecase.convert

import com.open.exchange.domain.models.common.Response
import com.open.exchange.domain.repository.OpenExchangeRepository
import kotlinx.coroutines.flow.Flow

class GetConvertRateUseCase(private val openExchangeRepository: OpenExchangeRepository) {
    suspend fun execute(): Flow<Response<Boolean>> =
        openExchangeRepository.getConvertList()
}
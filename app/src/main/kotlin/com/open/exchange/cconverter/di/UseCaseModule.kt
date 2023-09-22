package com.open.exchange.cconverter.di

import com.open.exchange.domain.repository.OpenExchangeRepository
import com.open.exchange.domain.usecase.convert.GetConvertRateUseCase
import com.open.exchange.domain.usecase.currency.GetCurrencyListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Provides
    @Singleton
    fun provideGetCurrencyListUseCase(openExchangeRepository: OpenExchangeRepository): GetCurrencyListUseCase =
        GetCurrencyListUseCase(openExchangeRepository = openExchangeRepository)

    @Provides
    @Singleton
    fun provideGetConvertListUseCase(openExchangeRepository: OpenExchangeRepository): GetConvertRateUseCase =
        GetConvertRateUseCase(openExchangeRepository = openExchangeRepository)
}
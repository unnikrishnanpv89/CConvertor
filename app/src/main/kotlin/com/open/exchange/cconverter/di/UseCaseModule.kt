package com.open.exchange.cconverter.di

import com.open.exchange.domain.repository.OpenExchangeRepository
import com.open.exchange.domain.usecase.convert.GetConvertRateUseCase
import com.open.exchange.domain.usecase.currency.GetCurrencyListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {


    @Provides
    @ViewModelScoped
    fun provideGetCurrencyListUseCase(openExchangeRepository: OpenExchangeRepository): GetCurrencyListUseCase =
        GetCurrencyListUseCase(openExchangeRepository = openExchangeRepository)

    @Provides
    @ViewModelScoped
    fun provideGetConvertListUseCase(openExchangeRepository: OpenExchangeRepository): GetConvertRateUseCase =
        GetConvertRateUseCase(openExchangeRepository = openExchangeRepository)
}
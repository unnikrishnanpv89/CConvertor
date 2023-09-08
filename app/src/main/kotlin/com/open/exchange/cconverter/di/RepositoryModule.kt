package com.open.exchange.cconverter.di

import com.open.exchange.data.dao.CurrencyDao
import com.open.exchange.data.local.datastore.SharedPrefenceStore
import com.open.exchange.data.remote.ExchangeDataSource
import com.open.exchange.data.remote.openexchange.APIService
import com.open.exchange.data.remote.openexchange.OpenExchangeDataSource
import com.open.exchange.data.repository.OpenExchangeRepositoryImpl
import com.open.exchange.domain.repository.OpenExchangeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideExchangeDataSource(apiService: APIService): ExchangeDataSource {
        return OpenExchangeDataSource(apiService = apiService)
    }

    @Provides
    @ViewModelScoped
    fun providesExchangeRepository(exchangeDataSource: ExchangeDataSource,
                                   currencyDao: CurrencyDao,
                                   preferenceStore: SharedPrefenceStore): OpenExchangeRepository =
        OpenExchangeRepositoryImpl(openApiDataSource = exchangeDataSource,
            currencyDao = currencyDao,
            preferenceStore = preferenceStore)

}
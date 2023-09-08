package com.open.exchange.cconverter.di

import android.app.Application
import android.content.Context
import com.open.exchange.data.dao.AppDatabase
import com.open.exchange.data.local.datastore.SharedPrefenceStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object DatabaseModule {

    @ViewModelScoped
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @ViewModelScoped
    @Provides
    fun provideCurrencyDao(db: AppDatabase) = db.getCurrencyListDao()
}

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule{
    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): SharedPrefenceStore {
        return SharedPrefenceStore(context)
    }
}
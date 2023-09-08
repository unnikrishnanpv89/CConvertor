package com.open.exchange.data.repository

import com.open.exchange.data.dao.CurrencyDao
import com.open.exchange.data.local.datastore.SharedPrefenceStore
import com.open.exchange.data.local.datastore.SharedPrefenceStore.Companion.RESPONSE_TIMESTAMP
import com.open.exchange.data.mappers.convert.ConvertMapper
import com.open.exchange.data.mappers.currency.CurrencyMapper
import com.open.exchange.data.remote.ExchangeDataSource
import com.open.exchange.domain.models.common.Response
import com.open.exchange.domain.repository.OpenExchangeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform

class OpenExchangeRepositoryImpl(private val  openApiDataSource: ExchangeDataSource,
    private val currencyDao: CurrencyDao,
    private val preferenceStore: SharedPrefenceStore): OpenExchangeRepository {

    override suspend fun getCurrencyList(): Flow<Response<Boolean>> =
        openApiDataSource.getCurrencyList().transform { response ->
            when(response){
                is Response.Loading -> { emit(Response.Loading()) }
                is Response.Fail -> { emit(Response.Fail(error = response.error))}
                is Response.Success -> {
                    val currencyList = CurrencyMapper().mapFromEntity(type = response.data)
                    currencyDao.insertAll(currencyList)
                    emit( Response.Success(data = true))
                }
            }
        }

    override suspend fun getConvertList(): Flow<Response<Boolean>> {
        val expireTime = preferenceStore.getLongValueFromDataStore(RESPONSE_TIMESTAMP).first()
        val count = currencyDao.getConvertCount().first()
        if (expireTime.isExpired() || count <= 0) {
            return openApiDataSource.getConvertList().transform { response ->
                when (response) {
                    is Response.Loading -> {
                        emit(Response.Loading())
                    }

                    is Response.Fail -> {
                        emit(Response.Fail(error = response.error))
                    }

                    is Response.Success -> {
                        val convertD = ConvertMapper().mapFromEntity(type = response.data)
                        /**
                         * since response time stamp is not updated based on request
                         * instead of saving it, am saving current time to
                         * limit 30 minutes gap between api calls
                         */
                        preferenceStore.saveLongValueToDataStore(
                            RESPONSE_TIMESTAMP,
                            System.currentTimeMillis()
                            //convertD.timestamp
                        )
                        convertD.rates.forEach { convert ->
                            currencyDao.updateConvertRate(convert.currencyCode, convert.rate)
                        }
                        emit(Response.Success(data = true))
                    }
                }
            }
        } else {
            return flow { emit(Response.Success(data = true)) }
        }
    }
}
const val MINUTES_30 = 1000*60*30

fun Long.isExpired(): Boolean =
    System.currentTimeMillis() - this > MINUTES_30

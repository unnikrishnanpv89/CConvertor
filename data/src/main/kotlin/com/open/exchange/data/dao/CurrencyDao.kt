package com.open.exchange.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.open.exchange.domain.models.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("Select * from Currency")
    fun getList() : Flow<List<Currency>>

    @Query("SELECT COUNT(*) FROM Currency where convertRate > 0")
    fun getConvertCount() : Flow<Int>

    @Query("Select convertRate from Currency where currencyCode = :code")
    suspend fun getRateForCurrency(code: String) : Float?

    @Query("Select * from Currency where isSelected=:selected")
    fun getSelectedList(selected: Boolean = true) : Flow<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencyList: List<Currency>)

    @Query("UPDATE Currency SET isSelected = :selected where currencyCode = :code")
    suspend fun updateCurrency(code: String, selected: Boolean)

    @Query("UPDATE Currency SET convertRate = :rate where currencyCode = :code")
    suspend fun updateConvertRate(code: String, rate: Float)

    @Query("UPDATE Currency SET isSelected = :selected")
    suspend fun updateAll(selected: Boolean)
}
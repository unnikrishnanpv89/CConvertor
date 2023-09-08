package com.open.exchange.data.mappers.currency

import com.google.gson.JsonObject
import com.open.exchange.data.mappers.MapperData
import com.open.exchange.domain.models.Currency

class CurrencyMapper:MapperData<JsonObject, List<Currency>> {

    override fun mapFromEntity(type: JsonObject): List<Currency> {
        val currencyList = ArrayList<Currency>()
        val keys = type.keySet()
        keys.forEach { key ->
            val value = type.get(key).toString()
            currencyList.add(
                Currency(
                    currencyName = value.replace(Regex("^\"|\"$"), ""),
                    currencyCode = key,
                    isSelected = false
                )
            )
        }
        return currencyList
    }
}
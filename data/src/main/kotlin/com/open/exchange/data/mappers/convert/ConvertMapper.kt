package com.open.exchange.data.mappers.convert

import android.util.Log
import com.open.exchange.data.mappers.MapperData
import com.open.exchange.data.models.ConvertModel
import com.open.exchange.domain.models.ConvertModelD
import com.open.exchange.domain.models.ConvertRateD

private const val TAG = "ConvertMapper"
class ConvertMapper() : MapperData<ConvertModel, ConvertModelD> {

    override fun mapFromEntity(type: ConvertModel): ConvertModelD {
        val rates = ArrayList<ConvertRateD>()
        val keys = type.rates?.keySet() ?: emptyList()
        if(keys.isNotEmpty()) {
            keys.forEach { key ->
                try {
                    val value = type.rates?.get(key).toString().toFloat()
                    rates.add(
                        ConvertRateD(
                            currencyCode = key,
                            rate = value
                        )
                    )
                }catch (e: NumberFormatException){
                    Log.e(TAG, "excpetion ${e.localizedMessage}")
                }
            }
        }
        return ConvertModelD(
            rates = rates,
            timestamp = type.timestamp ?: 0
        )
    }
}
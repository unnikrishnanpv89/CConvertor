package com.open.exchange.cconverter.presentation.utils

import com.open.exchange.domain.models.Currency
import com.open.exchange.domain.models.ResultModel

fun List<Currency>.toResultList(resultList: MutableList<ResultModel>): MutableList<ResultModel>{
    val returnList = ArrayList<ResultModel>(this.size)
    this.forEach { currency ->
        val index = resultList.indexOfFirst { it.currencyCode == currency.currencyCode }
        val result = if(index >= 0) resultList[index] else ResultModel.DEFAULT
        returnList.add(
            ResultModel(
                currencyCode = currency.currencyCode,
                result = result.result,
                currencyName = currency.currencyName,
                source = result.source,
                inputValue = result.inputValue
            )
        )
    }
    return returnList
}
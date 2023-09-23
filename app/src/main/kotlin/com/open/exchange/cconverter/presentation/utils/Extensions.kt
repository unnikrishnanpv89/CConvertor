package com.open.exchange.cconverter.presentation.utils

import com.open.exchange.domain.models.Currency
import com.open.exchange.domain.models.ResultModel

fun List<Currency>.toResultList(resultList: MutableList<ResultModel>): MutableList<ResultModel>{
    val returnList = arrayListOf<ResultModel>()
    val inputList = this.toMutableList()
    resultList.forEach { result ->
        if(inputList.indexOfFirst { it.currencyCode == result.currencyCode } >= 0 ) {
            returnList.add(result)
            inputList.removeIf { it.currencyCode == result.currencyCode }
        }
    }
    inputList.forEach { currency ->
        returnList.add( ResultModel(
            currencyCode = currency.currencyCode,
            result = 0F,
            currencyName = currency.currencyName,
            source = String(),
            inputValue = 0F
        ))
    }
    return returnList.toMutableList()
}
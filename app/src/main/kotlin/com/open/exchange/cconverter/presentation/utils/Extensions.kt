package com.open.exchange.cconverter.presentation.utils

import com.open.exchange.domain.models.Currency
import com.open.exchange.domain.models.ResultModel

fun List<Currency>.toResultList(resultList: MutableList<ResultModel>): MutableList<ResultModel>{
    val returnList = arrayListOf<ResultModel>()
    val inputList = this.toMutableList()
    resultList.forEach { result ->
        println("Adding result to return $result")
        if(inputList.indexOfFirst { it.currencyCode == result.currencyCode } >= 0 ) {
            returnList.add(result)
            inputList.removeIf { it.currencyCode == result.currencyCode }
        }
    }
    inputList.forEach { currency ->
        println("Adding currency to return $currency")
        returnList.add( ResultModel(
            currencyCode = currency.currencyCode,
            result = 0F,
            currencyName = currency.currencyName,
            source = String(),
            inputValue = 0F
        ))
    }
    return returnList.toMutableList()
    /*println(resultList.size)
    this.forEachIndexed { listIndex, currency ->
        val index = resultList.indexOfFirst { it.currencyCode == currency.currencyCode }
        val result = if(index >= 0) resultList[index] else ResultModel.DEFAULT
        val arrIndex = if(index >= 0) index else listIndex
        println(arrIndex)
        returnList[arrIndex] = ResultModel(
                currencyCode = currency.currencyCode,
                result = result.result,
                currencyName = currency.currencyName,
                source = result.source,
                inputValue = result.inputValue
            )
    }
    return returnList.toMutableList()*/
}
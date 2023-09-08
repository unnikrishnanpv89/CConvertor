package com.open.exchange.cconverter.presentation.screens.currencyselection

import com.open.exchange.domain.models.Currency

data class CurrencySelectionState(
    val currencyList: MutableList<Currency>,   //list of currency
    val isLoading: Boolean = true              // loading status for API
){
    companion object {
        val EMPTY = CurrencySelectionState(
            currencyList = arrayListOf())
    }
    var isSelectedAll: Boolean = currencyList.filter { it.isSelected }.size == currencyList.size
}

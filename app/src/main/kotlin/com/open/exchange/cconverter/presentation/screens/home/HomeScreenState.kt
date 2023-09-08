package com.open.exchange.cconverter.presentation.screens.home

import androidx.compose.ui.text.input.TextFieldValue
import com.open.exchange.domain.models.Currency
import com.open.exchange.domain.models.ResultModel


data class HomeScreenState(
    val isLoading: Boolean,
    val currencyList: MutableList<String>,
    val baseCurrency: String,
    val baseCurrencyIndex: Int,
    val inputValue: TextFieldValue,
    val selectedCurrency: MutableList<Currency>,
    val resultList: MutableList<ResultModel>
){
    companion object{
        val EMPTY = HomeScreenState(
            isLoading = true,
            baseCurrency = String(),
            baseCurrencyIndex = 0,
            inputValue = TextFieldValue(String()),
            currencyList = mutableListOf(),
            selectedCurrency = mutableListOf(),
            resultList = mutableListOf()
        )
    }
}


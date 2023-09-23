package com.open.exchange.cconverter.presentation.screens.settings

import androidx.compose.ui.text.input.TextFieldValue
import com.open.exchange.domain.models.Currency

data class SettingsDataState(
    val currencyList: MutableList<Currency>,
    val input: TextFieldValue
){
    companion object{
        val EMPTY = SettingsDataState(
            currencyList = mutableListOf(),
            input = TextFieldValue(String())
        )
    }
    var isSelectedAll: Boolean = currencyList.filter { it.isSelected }.size == currencyList.size
}

package com.open.exchange.cconverter.presentation.screens.initial

data class InitialScreenState(
    val selectedCurrencies: Int = 0
){
    var isDoneActivated: Boolean =
        selectedCurrencies > 0
}

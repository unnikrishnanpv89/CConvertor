package com.open.exchange.cconverter.presentation.screens.initial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.open.exchange.cconverter.presentation.screens.currencyselection.CurrencySelectionScreen
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme

@Composable
fun InitialScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(CConverterTheme.colors.background)
    ) {
        CurrencySelectionScreen()
    }
}
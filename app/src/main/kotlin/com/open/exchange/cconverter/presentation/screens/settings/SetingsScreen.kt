package com.open.exchange.cconverter.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.open.exchange.cconverter.presentation.screens.currencyselection.CurrencySelectionScreen

@Composable
fun SettingsScreen(){
   Column(modifier = Modifier.fillMaxSize()) {
       CurrencySelectionScreen()
   }
}
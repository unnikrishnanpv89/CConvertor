package com.open.exchange.cconverter.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.open.exchange.cconverter.R
import com.open.exchange.cconverter.presentation.ui.components.CurrencyShapeSelection
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()){
    val uiState = viewModel.uiState.collectAsState()
    val state = rememberLazyGridState()
    Column(modifier = Modifier.fillMaxSize()) {
       TextField(
           value = uiState.value.input,
           keyboardOptions = KeyboardOptions(
               keyboardType = KeyboardType.Text
           ),
           placeholder = {
               Text(
                   text = stringResource(R.string.search_currency),
                   color = CConverterTheme.colors.onBackground.copy(0.5f)
               )
           },

           singleLine = true,
           colors = TextFieldDefaults.colors(
               unfocusedContainerColor = CConverterTheme.colors.muted,
               focusedContainerColor = CConverterTheme.colors.navbarFocus,
               focusedTextColor = CConverterTheme.colors.onBackground,
               unfocusedTextColor = CConverterTheme.colors.onBackground,
               focusedIndicatorColor = CConverterTheme.colors.interactive,
               selectionColors = TextSelectionColors(
                   backgroundColor = CConverterTheme.colors.background,
                   handleColor = CConverterTheme.colors.navbarFocus
               )
           ),
           modifier = Modifier.fillMaxWidth(),
           onValueChange = {
               viewModel.onTextEdit(it)
           }
       )
       LazyVerticalGrid(columns = GridCells.Fixed(2),
           state = state,
           modifier = Modifier
               .fillMaxWidth()
               .padding(top = 10.dp)
               .background(CConverterTheme.colors.background),
           verticalArrangement = Arrangement.spacedBy(10.dp),
           horizontalArrangement = Arrangement.spacedBy(10.dp)){
           uiState.value.currencyList.forEach {
               item {
                   CurrencyShapeSelection(shadowText = it.currencyCode,
                       text =it.currencyName, isSelected =it.isSelected) {
                        viewModel.selectCurrency(it.currencyCode)
                   }
               }
           }
       }
   }
}
package com.open.exchange.cconverter.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.open.exchange.cconverter.R
import com.open.exchange.cconverter.presentation.ui.components.CurrencyShapeResult
import com.open.exchange.cconverter.presentation.ui.components.CustomDropDownMenu
import com.open.exchange.cconverter.presentation.ui.components.ProgressView
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme
import com.open.exchange.cconverter.presentation.utils.network.ConnectionState
import com.open.exchange.cconverter.presentation.utils.network.connectivityState
import com.open.exchange.domain.models.ResultModel

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()){
    val uiState = viewModel.uiState.collectAsState()
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    LaunchedEffect(key1 = Unit){
        /**
         * on launch of home screen we will try to fetch convert rate
         * if connected
         * 30 minutes expiry is already handled in data layer so, even if we call
         * API call will not happen
         */
        if(isConnected) {
            viewModel.getConvertRate()
        }else{
            viewModel.covertCurrency(uiState.value.inputValue)
        }
    }
    CConverterTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CConverterTheme.colors.background)
                .padding(top = 10.dp)
        ) {
            if (uiState.value.isLoading.not()) {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    //header
                    Text(
                        text = stringResource(R.string.home_title),
                        color = CConverterTheme.colors.onBackground,
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        text = stringResource(R.string.home_selection_label),
                        color = CConverterTheme.colors.onBackground,
                        style = MaterialTheme.typography.labelLarge
                    )
                    CustomDropDownMenu(
                        label = stringResource(R.string.currency_label),
                        uiState = uiState,
                        onItemSelected = { index, currency ->
                            viewModel.saveBaseCurrency(currency, index)
                        },
                    )
                    TextField(
                        value = uiState.value.inputValue,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.textfield_hint),
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
                            viewModel.covertCurrency(it)
                        }
                    )
                    ConversionTable(uiState.value.resultList)
                }
            } else {
                ProgressView(modifier = Modifier.align(Alignment.BottomCenter))
            }
        }
    }
}

@Composable
fun ConversionTable(resultList: MutableList<ResultModel>){
    LazyColumn(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        resultList.forEach {
            item {
                CurrencyShapeResult(it)
            }
        }
    }
}

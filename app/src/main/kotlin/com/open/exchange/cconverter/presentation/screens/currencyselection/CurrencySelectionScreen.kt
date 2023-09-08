package com.open.exchange.cconverter.presentation.screens.currencyselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.open.exchange.cconverter.R
import com.open.exchange.cconverter.presentation.ui.components.CurrencyShapeSelection
import com.open.exchange.cconverter.presentation.ui.components.ProgressView
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme
import com.open.exchange.cconverter.presentation.utils.network.ConnectionState
import com.open.exchange.cconverter.presentation.utils.network.connectivityState

@Composable
fun CurrencySelectionScreen(
    viewModel: CurrencySelectionViewModel = hiltViewModel()) {
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    LaunchedEffect(key1 = isConnected) {
        viewModel.checkIfFirstTime { firstTime ->
            if (firstTime) {
                viewModel.getCurrencyList { success ->
                    if(success){
                        viewModel.getConvertRate()
                    }
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isLoading.not()){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CConverterTheme.colors.background)
        ) {
            val state = rememberLazyGridState()
            LazyVerticalGrid(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(CConverterTheme.colors.background),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                uiState.currencyList.forEachIndexed { index, value ->
                    item {
                        CurrencyShapeSelection(shadowText = value.currencyCode,
                            text = value.currencyName,
                            isSelected = value.isSelected){
                            viewModel.selectCurrency(index = index)
                        }
                    }
                }
            }
            ExtendedFloatingActionButton(
                onClick = { viewModel.selectAll(uiState.isSelectedAll.not()) },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(alignment = Alignment.BottomEnd),
                containerColor = CConverterTheme.colors.primaryVariant.copy(alpha = 0.9f),
                contentColor = CConverterTheme.colors.onSurface,
                shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 20)),
                elevation = FloatingActionButtonDefaults.elevation(2.dp),
            ) {
                Text(
                    text = if (uiState.isSelectedAll.not())
                        stringResource(R.string.select_all) else stringResource(R.string.deselect_all)
                )
                Icon(imageVector = Icons.Outlined.Done, contentDescription = stringResource(R.string.add_selection))
            }
        }
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CConverterTheme.colors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressView()
        }
    }
}
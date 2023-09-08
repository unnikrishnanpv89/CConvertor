package com.open.exchange.cconverter.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme

@Composable
fun ProgressView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(color = CConverterTheme.colors.onBackground)
    }
}
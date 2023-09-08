package com.open.exchange.cconverter.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.open.exchange.cconverter.presentation.screens.home.HomeScreenState
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme

@Composable
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    notSetLabel: String? = null,
    uiState: State<HomeScreenState>,
    onItemSelected: (index: Int, item: String) -> Unit,
    drawItem: @Composable (String, Boolean, Boolean, () -> Unit) -> Unit = { item, selected, itemEnabled, onClick ->
        CustomDropdownMenuItem(
            text = item,
            selected = selected,
            enabled = itemEnabled,
            onClick = onClick,
        )
    },
) {
    var expanded by remember { mutableStateOf(false) }
    var position by remember { mutableFloatStateOf(0f) }
    val selectedIndex = remember{ mutableIntStateOf(0) }
    val items: MutableList<String> = uiState.value.currencyList
    LaunchedEffect(key1 = uiState.value.baseCurrencyIndex){
        selectedIndex.intValue = uiState.value.baseCurrencyIndex
    }
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        OutlinedTextField(
            label = { Text(label, color = CConverterTheme.colors.onBackground) },
            value = items.getOrNull(selectedIndex.intValue) ?: "",
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .background(CConverterTheme.colors.muted)
                .onGloballyPositioned {
                    position = it.size.height.toFloat()
                },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = CConverterTheme.colors.muted,
                unfocusedTextColor = CConverterTheme.colors.onBackground,
                focusedTextColor = CConverterTheme.colors.onBackground,
                focusedIndicatorColor = CConverterTheme.colors.interactive
            ),
            trailingIcon = {},
            onValueChange = { },
            readOnly = true,
        )

        // Transparent clickable surface on top of OutlinedTextField
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable(enabled = enabled) { expanded = true },
            color = Color.Transparent,
        ) {}
    }

    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false },
        ) {
            MaterialTheme {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = position.dp),
                    color = CConverterTheme.colors.muted,
                ) {
                    val listState = rememberLazyListState()
                    if (selectedIndex.intValue > -1) {
                        LaunchedEffect(Unit) {
                            listState.scrollToItem(index = selectedIndex.intValue)
                        }
                    }

                    LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                        if (notSetLabel != null) {
                            item {
                                CustomDropdownMenuItem(
                                    text = notSetLabel,
                                    selected = false,
                                    enabled = false,
                                    onClick = { },
                                )
                            }
                        }
                        itemsIndexed(items) { index, item ->
                            val selectedItem = index == selectedIndex.intValue
                            drawItem(
                                item,
                                selectedItem,
                                true
                            ) {
                                onItemSelected(index, item)
                                expanded = false
                            }

                            if (index < items.lastIndex) {
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp),
                                    color = CConverterTheme.colors.onBackground)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomDropdownMenuItem(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = when {
        !enabled -> CConverterTheme.colors.muted.copy(alpha = 0.5f)
        selected -> CConverterTheme.colors.muted.copy(alpha = 1f)
        else -> CConverterTheme.colors.muted.copy(alpha = 1f)
    }

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(modifier = Modifier
            .clickable(enabled) { onClick() }
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(
                text = text,
                color = CConverterTheme.colors.onBackground,
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}
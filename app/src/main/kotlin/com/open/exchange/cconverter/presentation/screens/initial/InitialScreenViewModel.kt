package com.open.exchange.cconverter.presentation.screens.initial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.open.exchange.data.dao.CurrencyDao
import com.open.exchange.data.local.datastore.SharedPrefenceStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialScreenViewModel @Inject constructor(
    private val currencyDao: CurrencyDao,
    val prefenceStore: SharedPrefenceStore
): ViewModel() {

    private val _uiState = MutableStateFlow(InitialScreenState())
    val uiState: StateFlow<InitialScreenState> = _uiState

    init {
        observeSelection()
    }

    private fun observeSelection() {
        viewModelScope.launch {
            currencyDao.getSelectedList().collectLatest {
                _uiState.value = _uiState.value.copy(selectedCurrencies = it.size)
            }
        }
    }
}
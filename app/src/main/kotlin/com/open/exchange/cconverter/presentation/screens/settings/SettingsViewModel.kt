package com.open.exchange.cconverter.presentation.screens.settings

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.open.exchange.data.dao.CurrencyDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val currencyDao: CurrencyDao):ViewModel() {

    private val _uiState = MutableStateFlow(SettingsDataState.EMPTY)
    val uiState: StateFlow<SettingsDataState> = _uiState

    init {
        collectCurrencyList()
    }

    private fun collectCurrencyList() {
        viewModelScope.launch {
            val currencyList = currencyDao.getList().first()
            _uiState.value = uiState.value.copy(currencyList = currencyList.toMutableList())
        }
    }

    internal fun onTextEdit(value: TextFieldValue){
        _uiState.value = _uiState.value.copy(input = value)
        viewModelScope.launch{
            val result = currencyDao.searchCurrency(value.text).first()
            _uiState.value = uiState.value.copy(currencyList = result.toMutableList())
        }
    }

    /**
     * update isSelected true/false
     */
    internal fun selectCurrency(code: String){
        val index = _uiState.value.currencyList.indexOfFirst { it.currencyCode == code }
        if(index >= 0) {
            val currency = _uiState.value.currencyList[index]
            val resultList = _uiState.value.currencyList.toMutableList()
            resultList[index] = currency.copy(isSelected = currency.isSelected.not())
            _uiState.value = _uiState.value.copy(currencyList = resultList)
            viewModelScope.launch {
                currencyDao.updateCurrency(
                    code = code,
                    selected = currency.isSelected.not()
                )
            }

        }
    }

}
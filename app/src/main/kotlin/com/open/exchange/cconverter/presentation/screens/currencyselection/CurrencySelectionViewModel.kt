package com.open.exchange.cconverter.presentation.screens.currencyselection

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.open.exchange.data.dao.CurrencyDao
import com.open.exchange.data.local.datastore.SharedPrefenceStore
import com.open.exchange.data.local.datastore.SharedPrefenceStore.Companion.IS_FIRSTTIME
import com.open.exchange.domain.models.common.Response
import com.open.exchange.domain.usecase.convert.GetConvertRateUseCase
import com.open.exchange.domain.usecase.currency.GetCurrencyListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CurrencySelectionViewModel"

@HiltViewModel
class CurrencySelectionViewModel @Inject constructor(
    private val getCurrencyListUseCase: GetCurrencyListUseCase,
    private val getConvertRateUseCase: GetConvertRateUseCase,
    private val currencyDao: CurrencyDao,
    private val  preferenceStore: SharedPrefenceStore
): ViewModel() {
    private val _uiState = MutableStateFlow(CurrencySelectionState.EMPTY)
    val uiState: StateFlow<CurrencySelectionState> = _uiState

    init {
        observerCurrency()
    }

    /**
     * realtime watching room db stream update ui
     */
    private fun observerCurrency(){
        viewModelScope.launch(Dispatchers.IO) {
            currencyDao.getList().collectLatest {
                _uiState.value = _uiState.value.copy(currencyList = it.toMutableList(),
                    isLoading = it.isEmpty())
            }
        }
    }

    /**
     * update isSelected true/false
     */
    internal fun selectCurrency(index: Int){
        val currency = _uiState.value.currencyList[index]
        viewModelScope.launch(Dispatchers.IO) {
            currencyDao.updateCurrency(
                code = currency.currencyCode,
                selected = currency.isSelected.not()
            )
        }
    }

    /**
     * Select or deselect all currencies
     *
     * isSelect : true if selected false otherwise
     */
    internal fun selectAll(isSelect: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            currencyDao.updateAll(isSelect)
        }
    }

    /**
     * check if the user is opening for first time
     * default true
     */
    fun checkIfFirstTime(callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val firstTime = preferenceStore.getBoolValueFromDataStore(IS_FIRSTTIME).first()
            callback(firstTime)
        }
    }

    /**
     * call open exchange api to fetch all currencies
     *
     * @param callback :callback to screen if success will call
     * convert API
     */
    internal fun getCurrencyList(callback: (Boolean) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            getCurrencyListUseCase.execute().collectLatest { response ->
                when(response) {
                    is Response.Loading -> {}
                    is Response.Fail -> {
                        Log.e(TAG, "get Currency list failed ${response.error}")
                    }
                    is Response.Success -> {
                        Log.i(TAG, "get Currency list success")
                        callback(response.data)
                    }
                }
            }
        }
    }

    /**
     * convert API call
     */
    internal fun getConvertRate(){
        viewModelScope.launch(Dispatchers.IO) {
            getConvertRateUseCase.execute().collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        Log.i(TAG, "get Convert rate success")
                    }

                    is Response.Fail -> {
                        Log.i(TAG, "get Convert rate failed ${response.error}")
                    }
                    is Response.Loading -> {}
                }
            }
        }
    }
}
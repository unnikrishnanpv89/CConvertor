package com.open.exchange.cconverter.presentation.screens.home

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.open.exchange.cconverter.presentation.utils.toResultList
import com.open.exchange.data.dao.CurrencyDao
import com.open.exchange.data.local.datastore.SharedPrefenceStore
import com.open.exchange.data.local.datastore.SharedPrefenceStore.Companion.DEFAULT_CURRENCY
import com.open.exchange.domain.models.common.Response
import com.open.exchange.domain.usecase.convert.GetConvertRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeScreenViewModel"
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val preferenceStore: SharedPrefenceStore,
    private val getConvertRateUseCase: GetConvertRateUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState.EMPTY)
    val uiState: StateFlow<HomeScreenState> = _uiState

    init {
        observeSelection()
        getCurrencyList()
        getBaseCurrency()
    }

    /**
     * This function loads default currency on each startus
     */
    private fun getBaseCurrency(){
        viewModelScope.launch {
            preferenceStore.getStringValueFromDataStore(DEFAULT_CURRENCY).collectLatest { code ->
                val currencyList = currencyDao.getList().first()
                val index = currencyList.indexOfFirst { it.currencyCode == code }
                if(index > 0) {
                    _uiState.value = _uiState.value.copy(baseCurrency = currencyList[index].currencyCode,
                        baseCurrencyIndex = index)
                }else if(currencyList.isNotEmpty()){
                    _uiState.value = _uiState.value.copy(baseCurrency = currencyList[0].currencyCode,
                        baseCurrencyIndex = 0)
                }
            }
        }
    }

    /**
     * helper function to save dafault base currency
     */
    internal fun saveBaseCurrency(currencyCode: String, index: Int){
        viewModelScope.launch {
            if(currencyCode.isNotEmpty()){
                val currency = currencyCode.split(":")
                if(currency.size > 1){
                    val code = currency[0]
                    preferenceStore.saveStringToDataStore(DEFAULT_CURRENCY, code)
                    _uiState.value = _uiState.value.copy(baseCurrency = code,
                        baseCurrencyIndex = index)
                    covertCurrency(uiState.value.inputValue)
                }
            }
        }
    }

    /**
     * this is the function which will load currency list from DB
     * Its mapped to CODE: NAME format in final selection list
     */
    private fun getCurrencyList() {
        viewModelScope.launch {
            val currencyList = currencyDao.getList().first()
            _uiState.value = _uiState.value.copy(currencyList = currencyList.map { currency ->
                "${currency.currencyCode}: ${currency.currencyName}"
            }.toMutableList(), isLoading = false)
        }
    }

    /**
     * observe currency selection from  settings screen
     *
     */
    private fun observeSelection() {
        viewModelScope.launch {
            currencyDao.getSelectedList().collectLatest { list ->
                    _uiState.value = _uiState.value.copy(
                        selectedCurrency = list.toMutableList(),
                        resultList = list.toResultList(uiState.value.resultList)
                )
                covertCurrency(uiState.value.inputValue)
            }
        }
    }

    /**
     * validity check is done here
     * its ok to be empty
     * in case exception previous value is retained
     * Also we are restricting max values to 15 length
     */
    internal fun covertCurrency(textFieldValue: TextFieldValue) {
        Log.d(TAG, "Convert Currency ${textFieldValue.text}")
        viewModelScope.launch {
            try {
                if (textFieldValue.text.isEmpty()) {
                    val resultList = _uiState.value.resultList.map{ it.copy(result = 0.0F ) }
                    _uiState.value = _uiState.value.copy(inputValue = textFieldValue,
                        resultList = resultList.toMutableList())
                    //restricting max inout length to 15
                } else if(textFieldValue.text.length < 15) {
                    val input = textFieldValue.text.toFloat()
                    _uiState.value = _uiState.value.copy(inputValue = textFieldValue)
                    val sourceCurrencyCode = uiState.value.baseCurrency
                    uiState.value.selectedCurrency.forEach { target ->
                        val usdToSourceCurrencyRate = currencyDao
                            .getRateForCurrency(sourceCurrencyCode)
                        val usdToTargetCurrencyRate = currencyDao.getRateForCurrency(target.currencyCode)
                        if(usdToSourceCurrencyRate != null && usdToTargetCurrencyRate != null) {
                            val resultExist: Boolean = try {
                                uiState.value.resultList.first {
                                    it.source == sourceCurrencyCode
                                            && it.inputValue == input
                                            && it.currencyCode == target.currencyCode
                                }
                                Log.d(TAG, "Result exist, so returning")
                                true
                            }catch (e: NoSuchElementException){
                                Log.d(TAG, "Result doesn't exist, so calculating")
                                false
                            }
                            if(resultExist.not()) {
                                val result = convertCurrency(
                                    input,
                                    usdToSourceCurrencyRate,
                                    usdToTargetCurrencyRate
                                )
                                updateResult(
                                    result = result,
                                    currencyCode = target.currencyCode,
                                    source = sourceCurrencyCode,
                                    input = input
                                )
                                Log.d(
                                    TAG, "Convert rate for $sourceCurrencyCode to " +
                                            "${target.currencyCode} = $result"
                                )
                            }
                        }
                    }
                }
            } catch (e: NumberFormatException) {
                Log.e(TAG, "Invalid input $textFieldValue")
            }
        }
    }

    /**
     * Helper function which will update result list in uistate
     */
    private fun updateResult(result: Float, currencyCode: String, source: String, input: Float) {
        val resultList = uiState.value.resultList.toMutableList()
        val index = resultList.indexOfFirst { it.currencyCode == currencyCode }
        if(index >= 0) {
            resultList[index] = resultList[index].copy(result = result, source = source,
                inputValue = input)
        }
        _uiState.value = _uiState.value.copy(resultList = resultList)
    }

    /**
     * API call to get convert rate
     * duplicate API call is restricted at data layer based on
     * last request result
     */
    internal fun getConvertRate(){
        Log.d(TAG, "getConvertRate")
        viewModelScope.launch {
            getConvertRateUseCase.execute().collectLatest { response ->
                when (response) {
                    is Response.Success -> {
                        Log.i(TAG, "getConvertRate success")
                        covertCurrency(uiState.value.inputValue)
                    }

                    is Response.Fail -> {
                        Log.i(TAG, "getConvertRate failed ${response.error}")
                    }
                    is Response.Loading -> {}
                }
            }
        }
    }

    /**
     * Main conversion function from source to target
     */
    private fun convertCurrency(
        amountInSourceCurrency: Float,
        usdToSourceCurrencyRate: Float,
        usdToTargetCurrencyRate: Float
    ): Float {
        // Convert from source currency to USD
        val amountInUSD = amountInSourceCurrency / usdToSourceCurrencyRate

        // Convert from USD to target currency
        return amountInUSD * usdToTargetCurrencyRate
    }

    fun swap(fromIndex: Int, toIndex: Int) {
        val list = uiState.value.resultList.toMutableList()
        val temp = list[fromIndex]
        list[fromIndex] = list[toIndex]
        list[toIndex] = temp
        _uiState.value = _uiState.value.copy(resultList = list)
    }
}
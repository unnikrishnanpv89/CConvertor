package com.open.exchange.cconverter.presentation.screens.activity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.open.exchange.data.local.datastore.SharedPrefenceStore
import com.open.exchange.data.local.datastore.SharedPrefenceStore.Companion.IS_FIRSTTIME
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val preferenceStore: SharedPrefenceStore): ViewModel(){

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _isFirstTime: MutableState<Boolean> = mutableStateOf(false)
    val isFirstTime: State<Boolean> = _isFirstTime

    init {
        viewModelScope.launch {
            preferenceStore.getBoolValueFromDataStore(IS_FIRSTTIME).collect {
                _isFirstTime.value = it
                _isLoading.value = false
            }
        }
    }
}
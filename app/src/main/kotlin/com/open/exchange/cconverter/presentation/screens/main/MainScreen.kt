package com.open.exchange.cconverter.presentation.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.open.exchange.cconverter.R
import com.open.exchange.cconverter.presentation.navigation.Navigation
import com.open.exchange.cconverter.presentation.navigation.NavigationScreen.HOME
import com.open.exchange.cconverter.presentation.navigation.NavigationScreen.INITIAL
import com.open.exchange.cconverter.presentation.navigation.NavigationScreen.SETTINGS
import com.open.exchange.cconverter.presentation.navigation.currentRoute
import com.open.exchange.cconverter.presentation.screens.bottomnavigation.BottomNavigationUI
import com.open.exchange.cconverter.presentation.screens.initial.InitialScreenViewModel
import com.open.exchange.cconverter.presentation.ui.components.appbar.AppBar
import com.open.exchange.cconverter.presentation.ui.components.appbar.TOPBAR
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme
import com.open.exchange.cconverter.presentation.utils.network.ConnectionState
import com.open.exchange.cconverter.presentation.utils.network.connectivityState
import com.open.exchange.data.local.datastore.SharedPrefenceStore.Companion.IS_FIRSTTIME
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MainScreen(isFirstTime: Boolean,
    viewModel: InitialScreenViewModel = hiltViewModel()){
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val uiState by viewModel.uiState.collectAsState()
    val isInit = remember { mutableStateOf(isFirstTime) }
    val callback = {
        coroutineScope.launch {
            viewModel.prefenceStore.saveBoolToDataStore(IS_FIRSTTIME, false)
        }
    }
    LaunchedEffect(key1 = Unit){
        println("LaunchedEffect")
        viewModel.prefenceStore.getBoolValueFromDataStore(IS_FIRSTTIME).collectLatest {
            isInit.value = it
        }
    }
    Scaffold(
        topBar = {
            when (currentRoute(navController)) {
                SETTINGS -> AppBar(navController, TOPBAR.SETTING, uiState, callback)
                INITIAL -> AppBar(navController, TOPBAR.INITIAL, uiState, callback)
                else -> {}
            }
        },
        bottomBar = {
            when(currentRoute(navController = navController)) {
                SETTINGS, HOME -> {
                    BottomNavigationUI(navController = navController)
                }
                else -> {}
            }
        },
        snackbarHost = {
            if (isConnected.not() && isInit.value) {
                Snackbar(
                    containerColor = CConverterTheme.colors.textSecondary,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.no_internet),
                        color = CConverterTheme.colors.background)
                }
            }
        },
        content = {
            Navigation(
                navController = navController,
                modifier = Modifier.padding(it),
                isInitial = isFirstTime
            )
        }
    )
}
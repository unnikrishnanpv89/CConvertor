package com.open.exchange.cconverter.presentation.screens.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.open.exchange.cconverter.presentation.screens.main.MainScreen
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var  splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            CConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CConverterTheme.colors.background
                ) {
                    MainScreen(isFirstTime = splashViewModel.isFirstTime.value)
                }
            }
        }
    }
}
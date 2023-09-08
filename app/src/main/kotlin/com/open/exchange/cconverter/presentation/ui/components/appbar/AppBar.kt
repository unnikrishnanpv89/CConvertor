package com.open.exchange.cconverter.presentation.ui.components.appbar

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.open.exchange.cconverter.R
import com.open.exchange.cconverter.presentation.navigation.NavigationScreen
import com.open.exchange.cconverter.presentation.screens.initial.InitialScreenState
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme
import com.open.exchange.cconverter.presentation.utils.network.ConnectionState
import com.open.exchange.cconverter.presentation.utils.network.connectivityState
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavController,
    topbar: TOPBAR,
    uiState: InitialScreenState,
    callback: () -> Job
){
    val context = LocalContext.current
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val title = when(topbar){
        TOPBAR.SETTING -> stringResource(id = R.string.nav_title_settings)
        TOPBAR.INITIAL -> stringResource(id = R.string.select_currency)
    }
    TopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = CConverterTheme.colors.primaryVariant),
        title = {
            Text(
                text = title,
                color = CConverterTheme.colors.onSurface,
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        actions = {
            if(topbar == TOPBAR.INITIAL) {
                IconButton(
                    onClick = {
                        if(uiState.isDoneActivated){
                            navigateToHomeScreen(navController = navController)
                            callback()
                        }else {
                            Toast.makeText(context, "Select at least one currency", Toast.LENGTH_LONG).show()
                        }
                 },
                /** enabled button only if connected or isDoneActivated **/
                enabled = isConnected || uiState.isDoneActivated) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = stringResource(R.string.done_action),
                    tint = CConverterTheme.colors.onSurface
                )
            }}
        }
    )
}

/**
 * navigate to home screen from currency selection
 */
fun navigateToHomeScreen(navController: NavController){
    navController.navigate(NavigationScreen.HOME)/*{
        popUpTo(NavigationScreen.INITIAL){
            inclusive = true
        }
    }*/
}

enum class TOPBAR{
    SETTING,
    INITIAL
}
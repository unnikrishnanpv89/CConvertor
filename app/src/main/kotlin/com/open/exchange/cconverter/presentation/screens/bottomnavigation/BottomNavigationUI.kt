package com.open.exchange.cconverter.presentation.screens.bottomnavigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.open.exchange.cconverter.presentation.navigation.currentRoute
import com.open.exchange.cconverter.presentation.ui.components.NavigationItem
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme
import com.open.exchange.cconverter.presentation.utils.Constants.STRING

/**
 * isFirstTime will prevent nav switch before initial data fetch
 */
@Composable
fun BottomNavigationUI(navController: NavController){
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current
    val selected = remember { mutableStateOf(false) }
    NavigationBar(
        containerColor = CConverterTheme.colors.navBarBg,
    ){
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Settings
        )
        items.forEach { item ->
            val titleId = remember(item.resource_title){
                context.resources.getIdentifier(item.resource_title, STRING, context.packageName)
            }
            selected.value = currentRoute(navController = navController) == item.route
            NavigationBarItem(
                label = {
                    Text(
                        text = stringResource(id = titleId),
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall,
                        color = if(selected.value) CConverterTheme.colors.interactive
                        else CConverterTheme.colors.onBackground)
                },
                selected = selected.value,
                icon = { item.icon(selected.value) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = CConverterTheme.colors.navbarFocus
                ),
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                }
            )
        }
    }
}
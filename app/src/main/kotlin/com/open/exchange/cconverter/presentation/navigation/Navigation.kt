package com.open.exchange.cconverter.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.open.exchange.cconverter.presentation.screens.home.HomeScreen
import com.open.exchange.cconverter.presentation.screens.initial.InitialScreen
import com.open.exchange.cconverter.presentation.screens.settings.SettingsScreen

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier,
    isInitial: Boolean
){
    NavHost(
        navController = navController,
        startDestination = if(isInitial) NavigationScreen.INITIAL else NavigationScreen.HOME,
        modifier = modifier) {
        composable(route = NavigationScreen.INITIAL){
            /** only during first start up **/
            InitialScreen()
        }
        composable(route = NavigationScreen.HOME) {
            HomeScreen()
        }
        composable(route = NavigationScreen.SETTINGS) {
            SettingsScreen()
        }
    }
}

@Composable
fun currentRoute(
    navController: NavController
):String?{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}


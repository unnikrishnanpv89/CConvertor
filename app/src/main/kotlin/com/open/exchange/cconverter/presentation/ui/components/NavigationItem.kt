package com.open.exchange.cconverter.presentation.ui.components

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.open.exchange.cconverter.R
import com.open.exchange.cconverter.presentation.navigation.NavigationScreen
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme
import com.open.exchange.cconverter.presentation.utils.Constants.NAV_TITLE_HOME
import com.open.exchange.cconverter.presentation.utils.Constants.NAV_TITLE_SETTINGS

sealed class NavigationItem(
    var route: String,
    var icon: @Composable (Boolean) -> Unit,
    var resource_title: String
){
    object Home: NavigationItem(
        NavigationScreen.HOME,
        { selected ->
            Icon(
                painter = painterResource(id = R.drawable.ic_nav_home),
                contentDescription = stringResource(id = R.string.nav_title_home),
                tint = if(selected) CConverterTheme.colors.interactive else
                    CConverterTheme.colors.onSurface,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .offset(x = 10.dp)
            )
        },
        resource_title = NAV_TITLE_HOME)
    object Settings: NavigationItem(
        NavigationScreen.SETTINGS,
        { selected ->
            Icon(
                painter = painterResource(id = R.drawable.ic_nav_settings),
                contentDescription = stringResource(id = R.string.nav_title_settings),
                tint = if(selected) CConverterTheme.colors.interactive else
                    CConverterTheme.colors.onSurface,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .offset(x = 10.dp)
            )
        },
        resource_title = NAV_TITLE_SETTINGS)
}

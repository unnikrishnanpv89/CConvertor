package com.open.exchange.cconverter.presentation.ui.theme

import android.app.Activity
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView

private val DarkColorPalette = ConverterColor(
    primary = shape_dark,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.DarkGray,
    onBackground = Color.White,
    onSurface = Color.White,
    brand = Shadow1,
    iconSecondary = Neutral0,
    uiBorder =  Neutral3,
    uiBackground = Neutral8,
    textSecondary = Neutral0,
    textHelp = Neutral1,
    muted = TextAndIcon,
    default = Color.White,
    interactive = InteractiveDark,
    buttonBackround = Color.White.copy(alpha = .1f),
    bgMuted = Bg_Muted,
    navbarFocus = nav_bar_focus_dark,
    navBarBg = navbar_bg_dark,
    dividerDefault = divider_default_dark,
    disabled = Color.White.copy(alpha = 0.4f),
    isDark = true,
    mediumTransparency = Color.White.copy(alpha = 0.4f)
)

private val LightColorPalette = ConverterColor(
    brand = Shadow5,
    primary = shape_light,
    primaryVariant = Purple700,

    // Other default colors to override
    secondary = Teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    textSecondary = Neutral7,
    onSecondary = Color.Gray,
    onBackground = Color.Black,
    onSurface = Color.LightGray,
    uiBackground = Neutral0,
    iconSecondary = Neutral7,
    uiBorder =  Neutral4,
    textHelp = Neutral6,
    isDark = false,
    muted = TextAndIcon_Light,
    default = Color.Black,
    bgMuted = Bg_Muted,
    navbarFocus = nav_bar_focus,
    navBarBg = navbar_bg,
    disabled = text_disabled,
    buttonBackround = Button_Backround_Light,
    dividerDefault = divider_default_light,
    interactive = InteractiveLight,
    mediumTransparency = Color.Black.copy(alpha = 0.4f)
)

/**
 * custom Color Palette
 */
@Stable
class ConverterColor(
    brand: Color,
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    background: Color,
    surface: Color,
    onPrimary: Color,
    textSecondary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    uiBackground: Color,
    iconPrimary: Color = brand,
    iconSecondary: Color,
    uiBorder: Color,
    textHelp: Color,
    isDark: Boolean,
    muted: Color,
    default: Color,
    interactive: Color,
    buttonBackround: Color,
    bgMuted: Color,
    disabled: Color,
    navbarFocus: Color,
    navBarBg: Color,
    dividerDefault: Color,
    mediumTransparency: Color,
){
    var primary by mutableStateOf(primary)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var background by mutableStateOf(background)
        private set
    var surface by mutableStateOf(surface)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var onSurface by mutableStateOf(onSurface)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var brand by mutableStateOf(brand)
        private set
    var iconPrimary by mutableStateOf(iconPrimary)
        private set
    var iconSecondary by mutableStateOf(iconSecondary)
        private set
    var uiBorder by mutableStateOf(uiBorder)
        private set
    var textHelp by mutableStateOf(textHelp)
        private set
    var muted by mutableStateOf(muted)
        private set
    var default by mutableStateOf(default)
        private set
    var interactive by mutableStateOf(interactive)
        private set
    var buttonBackround by mutableStateOf(buttonBackround)
        private set
    var bgMuted by mutableStateOf(bgMuted)
        private set
    var navbarFocus by mutableStateOf(navbarFocus)
        private set
    var disabled by mutableStateOf(disabled)
        private set
    var navBarBg by mutableStateOf(navBarBg)
        private set
    var dividerDefault by mutableStateOf(dividerDefault)
        private set
    var mediumTransparency by mutableStateOf(mediumTransparency)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun copy(): ConverterColor = ConverterColor(
        brand = brand,
        primary = primary,
        primaryVariant = primaryVariant,
        secondary = secondary,
        background = background,
        surface = surface,
        onPrimary = onPrimary,
        textSecondary = textSecondary,
        onSecondary = onSecondary,
        onBackground = onBackground,
        onSurface = onSurface,
        uiBackground = uiBackground,
        iconPrimary = iconPrimary,
        iconSecondary = iconSecondary,
        uiBorder = uiBorder,
        textHelp = textHelp,
        isDark = isDark,
        muted = muted,
        mediumTransparency = mediumTransparency,
        default = default,
        buttonBackround = buttonBackround,
        bgMuted = bgMuted,
        navbarFocus = navbarFocus,
        navBarBg = navBarBg,
        dividerDefault = dividerDefault,
        disabled = disabled,
        interactive = interactive
    )
    fun update(other: ConverterColor) {
        brand = other.brand
        uiBorder = other.uiBorder
        iconPrimary = other.iconPrimary
        iconSecondary = other.iconSecondary
        primary = other.primary
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        background = other.background
        surface = other.surface
        onPrimary = other.onPrimary
        onSecondary = other.onSecondary
        onBackground = other.onBackground
        uiBackground = other.uiBackground
        onSurface = other.onSurface
        textSecondary = other.textSecondary
        textHelp = other.textHelp
        isDark = other.isDark
        muted = other.muted
        default = other.default
        mediumTransparency = other.mediumTransparency
        buttonBackround = other.buttonBackround
        bgMuted = other.bgMuted
        navbarFocus = other.navbarFocus
        navBarBg = other.navBarBg
        dividerDefault = other.dividerDefault
        disabled = other.disabled
        interactive = other.interactive
    }
}

@Composable
fun CConverterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = Color.Transparent.hashCode()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
    ProvideColors(colors){
        MaterialTheme(
            colorScheme = debugColors(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}


object CConverterTheme {
    val colors: ConverterColor
        @Composable
        get() = localColors.current
}

@Composable
fun ProvideColors(
    colors: ConverterColor,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(localColors provides colorPalette, content = content)
}

private val localColors = staticCompositionLocalOf<ConverterColor> {
    error("No colors provided")
}

fun debugColors(
    isDarkMode: Boolean,
    debugColor: Color = if(isDarkMode) Color.Black else Color.White
) = ColorScheme(
    primary = debugColor,
    onPrimary = debugColor,
    primaryContainer = debugColor,
    onPrimaryContainer = debugColor,
    inversePrimary = debugColor,
    secondary = debugColor,
    onSecondary = debugColor,
    secondaryContainer = debugColor,
    onSecondaryContainer = debugColor,
    tertiary = debugColor,
    onTertiary = debugColor,
    tertiaryContainer = debugColor,
    onTertiaryContainer = debugColor,
    background = debugColor,
    onBackground = debugColor,
    surface = debugColor,
    onSurface = debugColor,
    surfaceVariant = debugColor,
    onSurfaceVariant = debugColor,
    surfaceTint = debugColor,
    inverseSurface = debugColor,
    inverseOnSurface = debugColor,
    error = debugColor,
    onError = debugColor,
    errorContainer = debugColor,
    onErrorContainer = debugColor,
    outline = debugColor,
    outlineVariant = debugColor,
    scrim = debugColor,
    surfaceBright = Color.Unspecified,
    surfaceDim = Color.Unspecified,
    surfaceContainer = Color.Unspecified,
    surfaceContainerHigh = Color.Unspecified,
    surfaceContainerHighest = Color.Unspecified,
    surfaceContainerLow = Color.Unspecified,
    surfaceContainerLowest = Color.Unspecified,
)
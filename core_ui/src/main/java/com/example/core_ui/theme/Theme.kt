package com.example.core_ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.core.prefs.SettingsPreferences
import com.example.core.di.ComponentProvider
import com.example.core.di.FeatureComponentProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

private val DarkColorScheme = darkColorScheme(
    primary = GreenMain,
    background = Color(0xFF121212),
    surface = Color(0xFF222222),
)

private val LightColorScheme = lightColorScheme(
    primary = GreenMain,
    background = BackgroundMain,
    surface = CardBackground,
)

@Composable
fun MoneyTalksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val appComponent = (context.applicationContext as FeatureComponentProvider).provideFeatureComponent()
    val settingsPreferences = appComponent.settingsPreferences()
    
    val appThemeId by settingsPreferences.appThemeId.collectAsState(initial = "default")
    
    val selectedTheme = ThemeProvider.getThemeById(appThemeId)
    
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme.copy(
            primary = selectedTheme.primaryColor,
            secondary = selectedTheme.secondaryColor,
            tertiary = selectedTheme.accentColor
        )
        else -> LightColorScheme.copy(
            primary = selectedTheme.primaryColor,
            secondary = selectedTheme.secondaryColor,
            tertiary = selectedTheme.accentColor
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

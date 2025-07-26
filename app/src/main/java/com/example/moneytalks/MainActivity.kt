package com.example.moneytalks

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.core.di.ComponentProvider
import com.example.core.network.NetworkMonitor
import com.example.core.prefs.SettingsPreferences
import com.example.core.sync.SyncManager
import com.example.core.utils.LocaleUtils
import com.example.feature_settings.ui.pinentry.PinEntryScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.core.workers.SyncWorker
import com.example.core_ui.theme.MoneyTalksTheme
import com.example.core.models.ThemeMode

class MainActivity : ComponentActivity() {
    private lateinit var syncManager: SyncManager
    private lateinit var settingsPreferences: SettingsPreferences
    private val activityScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Получаем зависимости через ComponentProvider
        val appComponent = (application as ComponentProvider).provideApplicationComponent()
        syncManager = appComponent.syncManager()
        settingsPreferences = appComponent.settingsPreferences()

        // Подписка на появление интернета
        activityScope.launch {
            NetworkMonitor.observe(this@MainActivity).collectLatest { isConnected ->
                if (isConnected) {
                    syncManager.triggerSyncOnNetworkAvailable()
                    Log.d("MainActivity", "Сработала синхронизация по интернету")
                }
            }
        }

        setContent {
            val pinCode by settingsPreferences.pinCode.collectAsState(initial = null)
            val themeMode by settingsPreferences.themeMode.collectAsState(initial = ThemeMode.SYSTEM.name)
            val appLanguage by settingsPreferences.appLanguage.collectAsState(initial = "ru")
            var isPinCorrect by remember { mutableStateOf(false) }
            
            // Определяем темную тему на основе настроек
            val isDarkTheme = when (ThemeMode.valueOf(themeMode)) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
            }
            
            // Применяем локализацию
            LaunchedEffect(appLanguage) {
                LocaleUtils.updateLocale(this@MainActivity, appLanguage)
            }
            
            MoneyTalksTheme(darkTheme = isDarkTheme) {
                if (pinCode != null && !isPinCorrect) {
                    PinEntryScreen(
                        onPinCorrect = {
                            isPinCorrect = true
                        },
                        settingsPreferences = settingsPreferences
                    )
                } else {
                    MainAppScreen()
                }
            }
        }
    }
}

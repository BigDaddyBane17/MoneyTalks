package com.example.moneytalks


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.core.network.NetworkMonitor
import com.example.core.sync.SyncManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.core.workers.SyncWorker
import com.example.core_ui.theme.MoneyTalksTheme


class MainActivity : ComponentActivity() {
    private lateinit var syncManager: SyncManager
    private val activityScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Инициализация SyncManager (через DI или вручную)
        syncManager = SyncManager(applicationContext)

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
            MoneyTalksTheme {
                MainAppScreen()
            }
        }
    }
}

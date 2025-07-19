package com.example.moneytalks


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.core.workers.SyncWorker
import com.example.core_ui.theme.MoneyTalksTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Принудительно запускаем синхронизацию при открытии приложения
        val syncWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .addTag("manual_sync")
            .build()
        
        WorkManager.getInstance(this).enqueue(syncWorkRequest)
        Log.d("MainActivity", "Запущена принудительная синхронизация")
        
        setContent {
            MoneyTalksTheme {
                MainAppScreen()
            }
        }
    }
}

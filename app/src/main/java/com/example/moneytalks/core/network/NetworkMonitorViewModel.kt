package com.example.moneytalks.core.network

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * ViewModel для мониторинга состояния интернет-соединения через NetworkMonitor.
 */
@HiltViewModel
class NetworkMonitorViewModel @Inject constructor(
    val networkMonitor: NetworkMonitor
): ViewModel() {

    init {
        networkMonitor.start()
    }

    override fun onCleared() {
        networkMonitor.stop()
        super.onCleared()
    }

}

package com.example.moneytalks.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Монитор сети для отслеживания состояния интернет-соединения в приложении.
 * Позволяет получать актуальную информацию о наличии подключения к интернету.
 */

class NetworkMonitor @Inject constructor(context: Context) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val _isConnected = MutableStateFlow(checkInternet())

    val isConnected: StateFlow<Boolean> = _isConnected

    private fun checkInternet(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(network) ?: return false
        return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) { _isConnected.value = true }

        override fun onLost(network: Network) { _isConnected.value = checkInternet() }

        override fun onUnavailable() { _isConnected.value = checkInternet() }
    }

    fun start() {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
    fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}

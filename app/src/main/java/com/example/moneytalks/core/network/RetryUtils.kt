package com.example.moneytalks.core.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


//на будущее - экспонента
suspend fun <T> retryIO(
    times: Int = 3,
    delayMillis: Long = 2000,
    block: suspend () -> T
): T = withContext(Dispatchers.IO) {
    repeat(times - 1) {
        try {
            Log.d("RETRY", "Attempt ${it+1}")
            return@withContext block()

        } catch (e: IOException) {
            throw e
        } catch (e: HttpException) {
            if (e.code() == 500) {
                delay(delayMillis)
            } else {
                throw e
            }
        }
    }
    Log.d("RETRY", "Attempt final")
    return@withContext block()
}

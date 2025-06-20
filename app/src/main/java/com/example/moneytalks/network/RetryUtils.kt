package com.example.moneytalks.network

import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> retryIO(
    times: Int = 3,
    delayMillis: Long = 2000,
    block: suspend () -> T
): T {
    repeat(times - 1) {
        try {
            return block()
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
    return block()
}
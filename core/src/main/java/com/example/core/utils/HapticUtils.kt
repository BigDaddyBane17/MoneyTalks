package com.example.core.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.example.core.models.HapticMode

object HapticUtils {
    
    fun performHapticFeedback(context: Context, hapticMode: HapticMode) {
        if (hapticMode == HapticMode.NONE) return
        
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        
        if (!vibrator.hasVibrator()) return
        
        val duration = when (hapticMode) {
            HapticMode.LIGHT -> 20L
            HapticMode.MEDIUM -> 50L
            HapticMode.HEAVY -> 100L
            HapticMode.NONE -> 0L
        }
        
        val amplitude = when (hapticMode) {
            HapticMode.LIGHT -> 64 // 1/4 от максимальной амплитуды
            HapticMode.MEDIUM -> 128 // 1/2 от максимальной амплитуды
            HapticMode.HEAVY -> 255 // максимальная амплитуда
            HapticMode.NONE -> 0
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(duration, amplitude)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }
} 
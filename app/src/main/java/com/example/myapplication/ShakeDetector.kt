package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import java.lang.Math.sqrt
import kotlin.math.sqrt

class ShakeDetector : SensorEventListener {
    private var shakeTimestamp: Long = 0
    private var shakeCount = 0

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val acceleration = sqrt(x * x + y * y + z * z)

        val now = System.currentTimeMillis()

        if (acceleration > SHAKE_THRESHOLD_GRAVITY) {
            if (shakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                return
            }

            if (shakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                shakeCount = 0
            }

            shakeTimestamp = now
            shakeCount++

            onShake()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun onShake() {
        // Do something on shake detection
    }

    companion object {
        private const val SHAKE_THRESHOLD_GRAVITY = 2.7f
        private const val SHAKE_SLOP_TIME_MS = 500
        private const val SHAKE_COUNT_RESET_TIME_MS = 3000
    }
}
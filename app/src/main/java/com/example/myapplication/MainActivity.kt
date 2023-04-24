package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : AppCompatActivity(),SensorEventListener{

    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private val SHAKE_THRESHOLD_GRAVITY = 5.7f
    private var mLastShakeTime: Long = 0
    private var isShakeDetectionEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val detectionToggle = findViewById<ToggleButton>(R.id.detection_toggle)
        detectionToggle.setOnCheckedChangeListener { _, isChecked ->
            isShakeDetectionEnabled = isChecked
        }
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent) {

        if (!isShakeDetectionEnabled) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        val gForce = Math.sqrt((gX * gX + gY * gY + gZ * gZ).toDouble()).toFloat()

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - mLastShakeTime > 2000) {
                mLastShakeTime = currentTime
                Toast.makeText(this, "Shake detected!", Toast.LENGTH_SHORT).show()

                //Notification Channel
                val currentTime = System.currentTimeMillis()
                val channelId = "shake_detection_channel"
                val channelName = "Shake Detection"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val notificationChannel = NotificationChannel(channelId, channelName, importance)
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(notificationChannel)

                val notificationBuilder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Shake Detected!")
                    .setContentText("Your device was shaken at ${SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(currentTime)}")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                notificationManager.notify(0, notificationBuilder.build())

            }
        }


        ////////
       // Add SOS COde HERe
                /////////
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //Do Nothing.
    }
}






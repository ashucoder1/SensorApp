//package com.example.myapplication
//
//import android.app.Notification
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.Service
//import com.example.myapplication.ShakeDetector
//import android.content.Context.SENSOR_SERVICE
//import android.content.Intent
//import android.hardware.Sensor
//import android.hardware.SensorManager
//import android.os.Build
//import android.os.IBinder
//import androidx.annotation.RequiresApi
//import androidx.core.app.NotificationCompat
//import androidx.core.content.ContextCompat.getSystemService
//import java.text.SimpleDateFormat
//import java.util.Locale
//
//class ShakeService: Service() {
//    private lateinit var sensorManager: SensorManager
//    private lateinit var accelerometer: Sensor
//    private val shakeDetector = ShakeDetector()
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate() {
//        super.onCreate()
//        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
//        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//        shakeDetector.setOnShakeListener {
//            showNotification()
//        }
//    }
//
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        startForeground(1, createNotification())
//        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI)
//        return START_STICKY
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        sensorManager.unregisterListener(shakeDetector)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotification(): Notification {
//        val channelId = "shake_detection_channel"
//        val channelName = "Shake Detection"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val notificationChannel = NotificationChannel(channelId, channelName, importance)
//        val notificationManager = getSystemService(NotificationManager::class.java)
//        notificationManager.createNotificationChannel(notificationChannel)
//
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Shake Detection is Running")
//            .setContentText("This service is listening for shake events.")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .build()
//
//        return notificationBuilder
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun showNotification() {
//        val channelId = "shake_detection_channel"
//        val channelName = "Shake Detection"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val notificationChannel = NotificationChannel(channelId, channelName, importance)
//        val notificationManager = getSystemService(NotificationManager::class.java)
//        notificationManager.createNotificationChannel(notificationChannel)
//
//        val currentTime = System.currentTimeMillis()
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Shake Detected!")
//            .setContentText("Your device was shaken at ${SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(currentTime)}")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .build()
//
//        notificationManager.notify(0, notificationBuilder)
//    }
//}
//

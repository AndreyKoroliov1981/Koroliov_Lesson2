package com.example.homework2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager

const val LOG_TAG = "my_tag"
const val MAX_COUNT = 92
const val ID_SERVICE = 42
const val CLOSE_ACTION = "close"

class FibonacciService : Service() {

    private var isStarted = false
    private var fN: Long = 0
    private var _fNliveData = MutableLiveData<Long>()
    var  fNliveData : LiveData<Long> = _fNliveData

    private var fNminus1: Long = 1
    private var fNminus2: Long = 0
    private var counter = -1


    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "FibonacciService onCreate")

    }

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.action.equals(CLOSE_ACTION)) {
            stopSelf()
        }

        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(
                NotificationChannel(
                    "fibonacci-channel",
                    "Fibonacci",
                    NotificationManager.IMPORTANCE_LOW
                )
            )

        if (!isStarted) {
            Thread {
                while (isStarted) {
                    calculateNumber()
                    createNotification()
                    sendNumberToActivity()

                    if (counter == MAX_COUNT) {
                        stopSelf()
                    }
                    Thread.sleep(1000)
                }
            }.start()
            isStarted = true
        }

        return START_STICKY
    }

    private fun calculateNumber() {
        counter++
        when (counter){
            0 -> fN = fNminus2
            1 -> fN = fNminus1
            else -> {
                fN = fNminus1 + fNminus2
                fNminus2 = fNminus1
                fNminus1 = fN
            }
        }
    }

    private fun createNotification() {

        val intentToEnd = Intent(this,FibonacciService::class.java)
        intentToEnd.action = CLOSE_ACTION
        val pendingIntent = PendingIntent.getService(this, 0, intentToEnd, 0)

        val currentNotification = NotificationCompat.Builder(this, "fibonacci-channel")
            .setContentTitle("Числа фибоначчи")
            .setContentText("FibonacciService fn($counter) = $fN")
            .setSmallIcon(R.drawable.ic_service)
            .addAction(R.drawable.ic_stop2, "stop service", pendingIntent)
            .build()
        startForeground(ID_SERVICE, currentNotification)
    }

    private fun sendNumberToActivity() {
        val intentToActivity = Intent(SERVICE_FIBONACCI)
        intentToActivity.putExtra("fN", "fn($counter) = $fN")
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentToActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        isStarted = false
        Log.d(LOG_TAG, "FibonacciService onDestroy")
    }
}
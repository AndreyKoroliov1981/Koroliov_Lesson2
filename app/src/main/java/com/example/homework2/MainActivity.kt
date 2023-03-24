package com.example.homework2

import android.app.ActivityManager
import android.content.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.homework2.databinding.ActivityMainBinding

const val SERVICE_FIBONACCI = "ServiceFibonacci"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val tokenPassingReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras
            if (bundle != null) {
                if (bundle.containsKey("fN")) {
                    val numberIndo = bundle.getString("fN")
                    binding.tvCount.text = numberIndo

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LocalBroadcastManager.getInstance(this).registerReceiver(tokenPassingReceiver, IntentFilter(SERVICE_FIBONACCI))

        val serviceClass = FibonacciService::class.java

        val intent = Intent(this, serviceClass)

        binding.btnStartService.setOnClickListener {
            if (!isServiceRunning(serviceClass)) {
                startForegroundService(intent)
                Log.d(LOG_TAG,"Service is running.")
            } else {
                Log.d(LOG_TAG,"Service is stopped.")
            }
        }

        binding.btnStopService.setOnClickListener {
            if (isServiceRunning(serviceClass)) {
                stopService(intent)
            } else {
                Log.d(LOG_TAG,"Service already stopped.")
            }
        }
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        // Loop through the running services
        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                // If the service is running then return true
                return true
            }
        }
        return false
    }
}
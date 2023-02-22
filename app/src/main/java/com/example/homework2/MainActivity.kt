package com.example.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.example.homework2.customView.AnalogClock
import com.example.homework2.customView.AnalogClockFields
import com.example.homework2.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var countdownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        startTimer(binding.analogClock)
    }

    private fun startTimer(view: AnalogClock) {
        countdownTimer = object : CountDownTimer(60000, 1000) {
            override fun onFinish() {
                countdownTimer.start()
            }

            override fun onTick(p0: Long) {
                val calendarInstance = Calendar.getInstance()
                val hour = calendarInstance.get(Calendar.HOUR)
                val minutes = calendarInstance.get(Calendar.MINUTE)
                val seconds = calendarInstance.get(Calendar.SECOND)
                val day = calendarInstance.get(Calendar.DAY_OF_MONTH)
                val month = calendarInstance.get(Calendar.MONTH) + 1
                view.analogClockFields = AnalogClockFields(hour, minutes, seconds, day, month)
            }
        }
        countdownTimer.start()
    }

    override fun onPause() {
        super.onPause()
        countdownTimer.cancel()
    }
}
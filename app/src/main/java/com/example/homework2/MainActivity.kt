package com.example.homework2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework2.databinding.ActivityMainBinding
import com.example.homework2.utils.SecondActivityContract


const val CURRENT_STATE = "current state"
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val activityLauncher = registerForActivityResult(SecondActivityContract()) { result->
        binding.tvMessage.text = result
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnNextActivity.setOnClickListener{
            val currentState = binding.tvMessage.text.toString()
            activityLauncher.launch(currentState)
        }
    }
}
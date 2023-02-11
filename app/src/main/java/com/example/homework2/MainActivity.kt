package com.example.homework2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework2.databinding.ActivityMainBinding


const val CURRENT_STATE = "current state"
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnNextActivity.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            val currentState = binding.tvMessage.text.toString()
            intent.putExtra(CURRENT_STATE, currentState)
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val message = intent?.getStringExtra(EXTRA_MESSAGE)
        message?.let {
            binding.tvMessage.text = it
        }

    }
}
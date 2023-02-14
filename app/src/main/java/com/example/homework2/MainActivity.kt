package com.example.homework2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }
    private fun setListeners(){
        binding.buttonToFrameLayout.setOnClickListener {
            val intent = Intent(this,FrameActivity::class.java)
            startActivity(intent)
        }
        binding.buttonToLinearLayout.setOnClickListener {
            val intent = Intent(this,LinearActivity::class.java)
            startActivity(intent)
        }
        binding.buttonToGridLayout.setOnClickListener {
            val intent = Intent(this,GridActivity::class.java)
            startActivity(intent)
        }
    }
}
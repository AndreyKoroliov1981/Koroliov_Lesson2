package com.example.homework2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework2.databinding.ActivityMainBinding
import com.example.homework2.databinding.ActivitySecondBinding

const val EXTRA_MESSAGE = "extra message"
class SecondActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnBack.setOnClickListener {
            var message = binding.etInputData.text.toString()
            if (message == "") {
                message = intent?.getStringExtra(CURRENT_STATE).toString()
            }
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(EXTRA_MESSAGE, message)
            startActivity(intent)
        }
    }
}
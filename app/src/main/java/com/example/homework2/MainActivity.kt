package com.example.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.homework2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()

    }

    private fun setListeners() {
        binding.btnContacts.setOnClickListener {
            showProgress(true)
        }

        binding.btnGallery.setOnClickListener {
            showProgress(true)
        }

        binding.btnVacancies.setOnClickListener {
            showProgress(true)
        }

        binding.progressBar.setOnClickListener {
            showProgress(false)
        }
    }

    private fun showProgress(showProgress: Boolean) {
        binding.groupOfficeAndButton.isVisible = !showProgress
        binding.progressBar.isVisible = showProgress
    }
}
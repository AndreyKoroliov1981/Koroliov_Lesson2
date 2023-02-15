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
            binding.groupOfficeAndButton.isVisible = false
            binding.progressBar.isVisible = true
        }

        binding.btnGallery.setOnClickListener {
            binding.groupOfficeAndButton.isVisible = false
            binding.progressBar.isVisible = true
        }

        binding.btnVacancies.setOnClickListener {
            binding.groupOfficeAndButton.isVisible = false
            binding.progressBar.isVisible = true
        }

        binding.progressBar.setOnClickListener {
            binding.groupOfficeAndButton.isVisible = true
            binding.progressBar.isVisible = false
        }
    }
}
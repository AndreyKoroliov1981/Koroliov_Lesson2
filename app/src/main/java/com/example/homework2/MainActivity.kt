package com.example.homework2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.homework2.databinding.ActivityMainBinding
import com.example.homework2.ui.main.MainFragment
import com.example.homework2.ui.office.OfficesFragment
import com.example.homework2.ui.vacancy.VacancyFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }

        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            if (fragment.tag == "main") {
                binding.bottomNavBar.isVisible = true
            }
        }

        binding.bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, MainFragment(), "main")
                        .commit()
                }
                R.id.action_vacancy -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, VacancyFragment(), "vacancy")
                        .commit()
                }
                R.id.action_offices -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, OfficesFragment(), "offices")
                        .commit()
                }
            }
            true
        }
    }

    fun changeBottomSelectedIDForMain() {
        binding.bottomNavBar.selectedItemId = R.id.action_home
    }
}
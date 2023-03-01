package com.example.homework2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.core.view.WindowInsetsCompat.toWindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
            binding.bottomNavBar.isVisible = true
        }

        setFragmentOnAttachListener()
        setBottomOnItemSelectedListener()

        window.decorView.setOnApplyWindowInsetsListener { view, insets ->
            val insetsCompat = toWindowInsetsCompat(insets, view)
            binding.bottomNavBar.isGone = insetsCompat.isVisible(ime())
            view.onApplyWindowInsets(insets)
        }
    }

    private fun setBottomOnItemSelectedListener() {
        binding.bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    replaceFragment(MainFragment(), FragmentsTags.Main.tag)
                }
                R.id.action_vacancy -> {
                    replaceFragment(VacancyFragment(), FragmentsTags.Vacancy.tag)
                }
                R.id.action_offices -> {
                    replaceFragment(OfficesFragment(), FragmentsTags.Offices.tag)
                }
            }
            true
        }
    }

    private fun setFragmentOnAttachListener() {
        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            when (fragment.tag) {
                FragmentsTags.Main.tag -> {
                    binding.bottomNavBar.isVisible = true
                }
                FragmentsTags.Vacancy.tag -> {
                    binding.bottomNavBar.isVisible = true
                }
                FragmentsTags.Offices.tag -> {
                    binding.bottomNavBar.isVisible = true
                }
                FragmentsTags.Detail.tag -> {
                    binding.bottomNavBar.isVisible = false
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment, tag)
            .commit()
    }

    fun changeBottomSelectedIDForMain() {
        binding.bottomNavBar.selectedItemId = R.id.action_home
    }

    fun setTitleActionBar(title: String) {
        supportActionBar?.title = title
    }
}
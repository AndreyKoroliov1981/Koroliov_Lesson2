package com.example.homework2.ui.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.homework2.R
import com.example.homework2.databinding.FragmentAuthBinding
import com.example.homework2.ui.main.MainFragment

const val password = "hello"

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            resources.getString(R.string.text_auth)
        addListeners()
        setButtonState()
    }

    private fun addListeners() {
        binding.tieName.addTextChangedListener {
            setButtonState()
        }

        binding.tiePassword.addTextChangedListener {
            setButtonState()
            binding.tilPassword.error = null
        }

        binding.btnInput.setOnClickListener {
            if (binding.tiePassword.text.toString() != password) {
                binding.tilPassword.error = resources.getString(R.string.textPasswordIncorrect)
            } else {
                binding.progressBar.isVisible = true
                binding.btnInput.isClickable = false
                Handler(Looper.getMainLooper()).postDelayed({
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, MainFragment(), "main")
                        .commit()

                }, 1000)
            }
        }
    }

    private fun setButtonState() {
        if ((binding.tieName.text.toString().isEmpty()) || (binding.tiePassword.text.toString()
                .isEmpty())
        ) {
            binding.btnInput.isClickable = false
            binding.btnInput.background.alpha = 128
        } else {
            binding.btnInput.isClickable = true
            binding.btnInput.background.alpha = 255
        }
    }
}
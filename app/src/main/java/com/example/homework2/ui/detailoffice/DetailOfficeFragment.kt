package com.example.homework2.ui.detailoffice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.homework2.MainActivity
import com.example.homework2.R
import com.example.homework2.databinding.FragmentDetailOfficeBinding
import com.example.homework2.ui.office.OfficesFragment.Companion.BUNDLE_KEY
import com.example.homework2.ui.office.OfficesFragment.Companion.REQUEST_KEY

class DetailOfficeFragment : Fragment() {
    private lateinit var binding: FragmentDetailOfficeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailOfficeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setTitleActionBar(resources.getString(R.string.textTitleAboutOffices))

        setFragmentResultListener(REQUEST_KEY) { key, bundle ->
            val id = bundle.getInt(BUNDLE_KEY)
            loadData(id)
        }
    }

    override fun onStart() {
        super.onStart()
        setupToolBar(true)
    }

    override fun onStop() {
        super.onStop()
        setupToolBar(false)
    }

    private fun setupToolBar(visibleBackArrow: Boolean) {
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(
            visibleBackArrow
        )
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(
            visibleBackArrow
        )
    }

    private fun loadData(id: Int) {
        when (id) {
            1 -> binding.tvInfo.text = resources.getString(R.string.textAboutGomel)
            2 -> binding.tvInfo.text = resources.getString(R.string.textAboutMoskow)
            3 -> binding.tvInfo.text = resources.getString(R.string.textAboutMinsk)
            4 -> binding.tvInfo.text = resources.getString(R.string.textAboutRostov)
            5 -> binding.tvInfo.text = resources.getString(R.string.textAboutKazan)
        }
    }

}
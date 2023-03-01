package com.example.homework2.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework2.MainActivity
import com.example.homework2.R
import com.example.homework2.databinding.FragmentMainBinding
import com.example.homework2.ui.main.model.AboutMessage
import com.example.homework2.ui.main.model.Header
import com.example.homework2.ui.main.model.Picture
import com.example.homework2.ui.main.recycler.*

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = listOf(
            Picture(pictureId = R.drawable.welcome_image),
            Header(
                pictureId = R.drawable.ic_logo,
                headerUp = resources.getString(R.string.textForBusiness),
                headerDown = resources.getString(R.string.textDevelopPO)
            ),
            AboutMessage(
                textUP = resources.getString(R.string.textTitleAbout),
                textDown = resources.getString(R.string.textAboutAston),
            )
        )
        (activity as? MainActivity)?.setTitleActionBar(resources.getString(R.string.textTitleMain))
        val delegateAdapterManager =
            AdapterDelegatesManager(PictureItemAdapterDelegate(), HeaderItemAdapterDelegate(), AboutMessageItemAdapterDelegate())
        binding.rvMain?.adapter = ComplexAdapter(
            delegateAdapterManager,
            list
        )
    }
}
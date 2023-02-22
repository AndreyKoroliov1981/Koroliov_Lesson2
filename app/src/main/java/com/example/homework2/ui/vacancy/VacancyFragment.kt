package com.example.homework2.ui.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.homework2.MainActivity
import com.example.homework2.R
import com.example.homework2.databinding.FragmentVacancyBinding
import customview.banner.BannerModel

class VacancyFragment : Fragment() {
    private lateinit var binding: FragmentVacancyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVacancyBinding.inflate(inflater, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            (activity as? MainActivity)?.changeBottomSelectedIDForMain()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            resources.getString(R.string.textTitleVacancy)
        setData()
    }

    private fun setData() {
        val vacancy1 = BannerModel(
            R.drawable.ic_network,
            60,
            60,
            "Разработчик ПО для wifi роутеров",
            "г. Москва",
            resources.getColor(R.color.white, null),
            resources.getColor(R.color.red, null)
        )
        binding.vacancy1.updateBannerView(vacancy1)

        val vacancy2 = BannerModel(
            R.drawable.ic_android,
            60,
            60,
            "Разработчик андройд приложений",
            "Удаленно",
            resources.getColor(R.color.white, null),
            resources.getColor(R.color.red, null)
        )
        binding.vacancy2.updateBannerView(vacancy2)

        val vacancy3 = BannerModel(
            R.drawable.ic_mac,
            60,
            60,
            "Разработчик IOS приложений",
            "г. Сочи",
            resources.getColor(R.color.grey, null),
            resources.getColor(R.color.black, null)
        )
        binding.vacancy3.updateBannerView(vacancy3)

        val vacancy4 = BannerModel(
            R.drawable.ic_bakery,
            60,
            60,
            "Повар",
            "г. Вологда",
            resources.getColor(R.color.black, null),
            resources.getColor(R.color.white, null)
        )
        binding.vacancy4.updateBannerView(vacancy4)
    }

}
package com.example.homework2.ui.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.homework2.MainActivity
import com.example.homework2.R
import com.example.homework2.databinding.FragmentVacancyBinding
import com.example.homework2.ui.vacancy.model.VacancyModel
import com.example.homework2.ui.vacancy.recycler.VacancyAdapter
import customview.banner.BannerModel

class VacancyFragment : Fragment(), VacancyAdapter.ItemClickListener {
    private lateinit var binding: FragmentVacancyBinding
    private var adapter = VacancyAdapter(this)
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
        (activity as? MainActivity)?.setTitleActionBar(resources.getString(R.string.textTitleVacancy))
        binding.rvVacancy.adapter = adapter
        adapter.updateList(getData())

        binding.svSearchText.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if ((text == "") || (text == null)) {
                    adapter.updateList(getData())
                    binding.svSearchText.queryHint = resources.getString(R.string.textSearchTextHint)
                } else adapter.updateList(filterVacancyList(text, getData()))
                return true
            }
        })
    }

    fun filterVacancyList(searchText: String, vacancyList: List<VacancyModel>): List<VacancyModel> {
        val newVacancyList = mutableListOf<VacancyModel>()
        for (i in vacancyList.indices) {
            if (vacancyList[i].fields.title.uppercase().contains(searchText.uppercase())) {
                newVacancyList.add(vacancyList[i])
                continue
            }
            if (vacancyList[i].fields.subtitle.uppercase().contains(searchText.uppercase())) {
                newVacancyList.add(vacancyList[i])
            }
        }
        return newVacancyList
    }

    override fun onItemClick(vacancy: VacancyModel) {
        Toast.makeText(
            requireContext(),
            "press vacancy - ${vacancy.fields.title}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun getData(): List<VacancyModel> {
        val dataVacancy = mutableListOf<VacancyModel>()
        for (i in 0 .. 5 ) {
            dataVacancy.add(
                VacancyModel(
                    id = 1 + i * 4,
                    fields = BannerModel(
                        image = R.drawable.ic_network,
                        imageWith = 60,
                        imageHeight = 60,
                        title = "Разработчик ПО для wifi роутеров ${1 + i * 4}",
                        subtitle = "г. Москва",
                        titlesColor = resources.getColor(R.color.white, null),
                        background = resources.getColor(R.color.red, null)
                    )
                )
            )
            dataVacancy.add(
                VacancyModel(
                    id = 2 + i * 4,
                    fields = BannerModel(
                        image = R.drawable.ic_android,
                        imageWith = 60,
                        imageHeight = 60,
                        title = "Разработчик андройд приложений ${2 + i * 4}",
                        subtitle = "Удаленно",
                        titlesColor = resources.getColor(R.color.white, null),
                        background = resources.getColor(R.color.red, null)
                    )
                )
            )
            dataVacancy.add(
                VacancyModel(
                    id = 3 + i * 4,
                    fields = BannerModel(
                        image = R.drawable.ic_mac,
                        imageWith = 60,
                        imageHeight = 60,
                        title = "Разработчик IOS приложений ${3 + i * 4}",
                        subtitle = "г. Сочи",
                        titlesColor = resources.getColor(R.color.grey, null),
                        background = resources.getColor(R.color.black, null)
                    )
                )
            )
            dataVacancy.add(
                VacancyModel(
                    id = 4 + i * 4,
                    fields = BannerModel(
                        image = R.drawable.ic_bakery,
                        imageWith = 60,
                        imageHeight = 60,
                        title = "Повар ${4 + i * 4}",
                        subtitle = "г. Вологда",
                        titlesColor = resources.getColor(R.color.black, null),
                        background = resources.getColor(R.color.white, null)
                    )
                )
            )
        }
        return dataVacancy
    }

}
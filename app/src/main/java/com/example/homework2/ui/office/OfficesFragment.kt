package com.example.homework2.ui.office

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.example.homework2.FragmentsTags
import com.example.homework2.MainActivity
import com.example.homework2.R
import com.example.homework2.databinding.FragmentOfficesBinding
import com.example.homework2.ui.detailoffice.DetailOfficeFragment
import com.example.homework2.ui.office.model.CityModel
import com.example.homework2.ui.office.recycler.OfficesAdapter
import com.example.homework2.ui.vacancy.model.VacancyModel
import com.example.homework2.ui.vacancy.recycler.VacancyAdapter

class OfficesFragment : Fragment(), OfficesAdapter.ItemClickListener {
    private lateinit var binding: FragmentOfficesBinding
    private var adapter = OfficesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOfficesBinding.inflate(inflater, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            (activity as? MainActivity)?.changeBottomSelectedIDForMain()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setTitleActionBar(resources.getString(R.string.textTitleOffices))
        binding.rvOffices.adapter = adapter
        adapter.updateList(getCity())
    }


    private fun gotoDetailFragment(id: Int) {
        setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to id))
        parentFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, DetailOfficeFragment(), FragmentsTags.Detail.tag)
            .addToBackStack(null)
            .commit()
    }

    private fun getCity(): List<CityModel> {
        val listCity = mutableListOf<CityModel>()
        listCity.add(CityModel.CityRuModel(id = 2, name = "Москва"))
        listCity.add(CityModel.CityRuModel(id = 5, name = "Казань"))
        listCity.add(CityModel.CityByModel(id = 3, name = "Минск"))
        listCity.add(CityModel.CityRuModel(id = 4, name = "Ростов-на-Дону"))
        listCity.add(CityModel.CityByModel(id = 1, name = "Гомель"))
        return listCity
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val BUNDLE_KEY = "bundleKey"
    }

    override fun onItemClick(cityId: Int) {
        Log.d("my_tag", "cityId = $cityId")
        gotoDetailFragment(cityId)
    }

}
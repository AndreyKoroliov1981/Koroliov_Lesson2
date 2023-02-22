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
import com.example.homework2.MainActivity
import com.example.homework2.R
import com.example.homework2.databinding.FragmentOfficesBinding
import com.example.homework2.ui.detailoffice.DetailOfficeFragment

class OfficesFragment : Fragment() {
    private lateinit var binding: FragmentOfficesBinding

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
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            resources.getString(R.string.textTitleOffices)
        setListeners()
    }

    private fun setListeners() {
        binding.cGomel.setOnClickListener {
            gotoDetailFragment(1)
        }
        binding.cMoskow.setOnClickListener {
            gotoDetailFragment(2)
        }
        binding.cMinsk.setOnClickListener {
            gotoDetailFragment(3)
        }
        binding.cRostov.setOnClickListener {
            gotoDetailFragment(4)
        }
        binding.cKazan.setOnClickListener {
            gotoDetailFragment(5)
        }
    }

    private fun gotoDetailFragment(id: Int) {
        setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to id))
        parentFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, DetailOfficeFragment(), "detail")
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val BUNDLE_KEY = "bundleKey"
    }

}
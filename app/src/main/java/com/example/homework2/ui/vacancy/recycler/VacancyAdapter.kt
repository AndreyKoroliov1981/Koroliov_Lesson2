package com.example.homework2.ui.vacancy.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.homework2.databinding.ItemVacancyBinding
import com.example.homework2.ui.vacancy.model.VacancyModel

class VacancyAdapter(private val onItemCLick: ItemClickListener) : Adapter<ViewHolder>() {
    private val differ = AsyncListDiffer(this, VacancyDiffUtilCallback())

    interface ItemClickListener {
        fun onItemClick(vacancy: VacancyModel)
    }

    fun updateList(list: List<VacancyModel>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return VacancyHolder(
            ItemVacancyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is VacancyHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }


    class VacancyDiffUtilCallback : DiffUtil.ItemCallback<VacancyModel>() {
        override fun areItemsTheSame(oldItem: VacancyModel, newItem: VacancyModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VacancyModel, newItem: VacancyModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class VacancyHolder(
        private val binding: ItemVacancyBinding,
    ) : ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val item = differ.currentList[absoluteAdapterPosition]
                item?.let { it -> onItemCLick.onItemClick(it) }
            }
        }

        fun bind(vacancy: VacancyModel) {
            binding.apply {
                bvVacancy.updateBannerView(vacancy.fields)
            }
        }
    }
}

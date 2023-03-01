package com.example.homework2.ui.office.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.R
import com.example.homework2.databinding.ItemOfficesByBinding
import com.example.homework2.databinding.ItemOfficesRuBinding
import com.example.homework2.ui.office.model.CityModel

class OfficesAdapter(private val onItemCLick: ItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, OfficeDiffUtilCallback())

    interface ItemClickListener {
        fun onItemClick(cityId: Int)
    }

    fun updateList(list: List<CityModel>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_offices_ru -> CityRuHolder(
                ItemOfficesRuBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            R.layout.item_offices_by -> CityByHolder(ItemOfficesByBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ))
            else -> {
                throw IllegalStateException("Unknown view type")
            }
        }
    }

    override fun getItemViewType(position: Int) = when (differ.currentList[position]) {
        is CityModel.CityRuModel -> R.layout.item_offices_ru
        is CityModel.CityByModel -> R.layout.item_offices_by
        null -> throw IllegalStateException("Unknown view")
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = differ.currentList[position]
        when (holder) {
            is CityRuHolder -> holder.bind(item as CityModel.CityRuModel)
            is CityByHolder -> holder.bind(item as CityModel.CityByModel)
        }
    }

    class OfficeDiffUtilCallback : DiffUtil.ItemCallback<CityModel>() {
        override fun areItemsTheSame(oldItem: CityModel, newItem: CityModel): Boolean {
            val isSameRepoItem =
                (oldItem is CityModel.CityRuModel) && (newItem is CityModel.CityRuModel)
                        && (oldItem.id == newItem.id)

            val isSameSeparatorItem =
                (oldItem is CityModel.CityByModel) && (newItem is CityModel.CityByModel)
                        && (oldItem.id == newItem.id)

            return isSameRepoItem || isSameSeparatorItem
        }

        override fun areContentsTheSame(oldItem: CityModel, newItem: CityModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class CityRuHolder(
        private val binding: ItemOfficesRuBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val item = differ.currentList[absoluteAdapterPosition] as CityModel.CityRuModel
                item.let { it -> onItemCLick.onItemClick(it.id) }
            }
        }

        fun bind(city: CityModel.CityRuModel) {
            binding.apply {
                tvCity.text = city.name
            }
        }
    }

    inner class CityByHolder(
        private val binding: ItemOfficesByBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val item = differ.currentList[absoluteAdapterPosition] as CityModel.CityByModel
                item.let { it -> onItemCLick.onItemClick(it.id) }
            }
        }

        fun bind(city: CityModel.CityByModel) {
            binding.apply {
                tvCity.text = city.name
            }
        }
    }
}
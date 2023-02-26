package com.example.homework2.ui.main.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.databinding.ItemHeaderBinding
import com.example.homework2.ui.main.model.Header

internal class HeaderItemAdapterDelegate : AdapterDelegate<DisplayableItem> {
    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is Header
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as HeaderViewHolder).bind(items[position] as Header)
    }

    inner class HeaderViewHolder(private val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: Header) {
            binding.ivLogo.setImageResource(header.pictureId)
            binding.tvTextForBusiness.text = header.headerUp
            binding.tvTextDevelopPO.text = header.headerDown
        }
    }
}
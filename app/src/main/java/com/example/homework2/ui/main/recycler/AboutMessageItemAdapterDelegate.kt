package com.example.homework2.ui.main.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.databinding.ItemAboutMessageBinding
import com.example.homework2.ui.main.model.AboutMessage

internal class AboutMessageItemAdapterDelegate : AdapterDelegate<DisplayableItem> {
    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is AboutMessage
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding =
            ItemAboutMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AboutMessageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as AboutMessageViewHolder).bind(items[position] as AboutMessage)
    }

    inner class AboutMessageViewHolder(private val binding: ItemAboutMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(aboutMessage: AboutMessage) {
            binding.tvTitleAbout.text = aboutMessage.textUP
            binding.tvTextAbout.text = aboutMessage.textDown
        }
    }
}
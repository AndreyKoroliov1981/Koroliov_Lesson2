package com.example.homework2.ui.main.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ComplexAdapter(
    private val delegateManager: AdapterDelegatesManager<DisplayableItem>,
    private var items: List<DisplayableItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateManager.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return delegateManager.getItemViewType(items, position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateManager.onBindViewHolder(items, position, holder)
    }
}
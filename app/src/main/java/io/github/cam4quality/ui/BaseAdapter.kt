package io.github.cam4quality.ui

import androidx.recyclerview.widget.RecyclerView
import io.github.cam4quality.contract.IdentifiableObject

abstract class BaseAdapter<T : IdentifiableObject>(private val items: MutableList<T> = mutableListOf()) :
    RecyclerView.Adapter<BaseViewHolder<T>>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    fun swapData(newData: List<T>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    fun removeItem(id: String) {
        val position = items.indexOfFirst { it.id == id }
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}
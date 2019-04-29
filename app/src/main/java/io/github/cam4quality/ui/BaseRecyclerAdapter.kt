package io.github.cam4quality.ui

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T>(private val items: MutableList<T> = mutableListOf()) :
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
}
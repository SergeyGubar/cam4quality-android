package io.github.cam4quality.ui

import io.github.cam4quality.contract.IdentifiableObject

abstract class BaseIdentifiableAdapter<T : IdentifiableObject>(private val items: MutableList<T> = mutableListOf()) :
    BaseRecyclerAdapter<T>() {

    fun removeItem(id: String) {
        val position = items.indexOfFirst { it.id == id }
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}
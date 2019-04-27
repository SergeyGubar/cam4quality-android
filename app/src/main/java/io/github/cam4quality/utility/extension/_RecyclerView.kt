package io.github.cam4quality.utility.extension

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.cam4quality.R
import io.github.cam4quality.ui.BottomDecoration
import io.github.cam4quality.ui.SpacesItemDecoration

fun RecyclerView.defaultSetup(adapter: RecyclerView.Adapter<*>) {
    with(this) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.context)
        this.adapter = adapter
        addItemDecoration(SpacesItemDecoration(this.context.getSize(R.dimen.padding_standard)))
        addItemDecoration(BottomDecoration(this.context.getSize(R.dimen.padding_big) * 4))
    }
}
package io.github.cam4quality.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BottomDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == parent.adapter!!.itemCount - 1) {
            outRect.bottom = space
        }
    }
}
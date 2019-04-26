package io.github.cam4quality.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val left: Int,
                           private val right: Int = left,
                           private val bottom: Int = left,
                           private val top: Int = left / 2) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = left
        outRect.right = right
        outRect.bottom = bottom
        outRect.top = top
    }
}
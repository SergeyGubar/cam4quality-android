package io.github.cam4quality.utility.extension

import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(resId: Int, attach: Boolean = false): View {
    return this.context.inflater.inflate(resId, this, attach)
}
package io.github.cam4quality.utility.extension

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

fun <T : View> Activity.bind(@IdRes idRes: Int): Lazy<T> = unsafeLazy { findViewById<T>(idRes) }

fun <T : View> Fragment.bind(@IdRes idRes: Int): Lazy<T> = unsafeLazy { view!!.findViewById<T>(idRes) }

fun <T : View> View.bind(@IdRes res: Int): Lazy<T> = unsafeLazy { findViewById<T>(res) }

fun <T : View> RecyclerView.ViewHolder.bind(@IdRes res: Int): Lazy<T> = unsafeLazy { itemView.findViewById<T>(res) }

fun <T> unsafeLazy(block: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) { block() }
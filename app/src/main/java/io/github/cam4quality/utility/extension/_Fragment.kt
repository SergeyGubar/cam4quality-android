package io.github.cam4quality.utility.extension

import androidx.fragment.app.Fragment

val Fragment.notNullContext
    get() = context!!

val Fragment.notNullFragmentManager
    get() = fragmentManager!!
package io.github.cam4quality.utility.extension

fun Double.toFixedNumberString(n: Int): String {
    return "%.${n}f".format(this)
}
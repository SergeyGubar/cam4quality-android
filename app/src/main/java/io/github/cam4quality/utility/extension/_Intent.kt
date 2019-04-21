package io.github.cam4quality.utility.extension

import android.content.Intent

fun filledIntent(block: Intent.() -> Unit): Intent {
    return Intent().apply(block)
}
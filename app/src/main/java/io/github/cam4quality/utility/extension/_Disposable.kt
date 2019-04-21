package io.github.cam4quality.utility.extension

import io.reactivex.disposables.Disposable

fun Disposable?.safelyDispose() {
    this?.let {
        if (!it.isDisposed) {
            it.dispose()
        }
    }
}
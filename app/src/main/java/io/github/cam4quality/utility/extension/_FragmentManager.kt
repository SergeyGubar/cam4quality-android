package io.github.cam4quality.utility.extension

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.func()
    transaction.commitAllowingStateLoss()
}

inline fun FragmentManager.inTransactionWithBackStackAndName(name: String?, func: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.func()
    transaction.addToBackStack(name)
    transaction.commitAllowingStateLoss()
}

inline fun FragmentManager.inTransactionWithBackStack(func: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.func()
    transaction.addToBackStack(null)
    transaction.commitAllowingStateLoss()
}
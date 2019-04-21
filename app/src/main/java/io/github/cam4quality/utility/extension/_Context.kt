package io.github.cam4quality.utility.extension

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T : AppCompatActivity> navigate(
    activity: AppCompatActivity,
    block: Intent.() -> Unit = { }
) {
    val intent = Intent(activity, T::class.java).apply(block)
    activity.startActivity(intent)
}

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

package io.github.cam4quality.ui

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import io.github.cam4quality.BaseActivity
import io.github.cam4quality.utility.helper.SharedPrefHelper
import org.koin.android.ext.android.inject
import java.util.*

abstract class BaseLocalizedActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBaseContextLocale(this)
    }

    private val prefHelper: SharedPrefHelper by inject()

    private fun updateBaseContextLocale(context: Context): Context {
        val language = prefHelper.getLanguage()
        val locale = Locale(language)
        Locale.setDefault(locale)
        // Not deprecated method doesn't work. Probably, will be used later
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResourcesLocale(context, locale)
        }*/

        return updateResourcesLocaleLegacy(context, locale)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    @Suppress("DEPRECATION")
    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context {
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

}
@file:Suppress("DEPRECATION")

package io.github.cam4quality.utility.helper

import android.app.Activity
import android.content.res.Configuration
import java.util.*

object LocaleHelper {

    const val LOCALE_LANG_ENG = "en"
    const val LOCALE_LANG_UA = "uk-UA"

    fun changeApplicationLocale(activity: Activity, locale: String) {
        val applicationConfiguration = setCurrentActivityLanguage(activity, locale)
        activity.resources.updateConfiguration(
            applicationConfiguration,
            activity.resources.displayMetrics
        )
        activity.onConfigurationChanged(applicationConfiguration)
    }

    // This method should be called every time when Activity is created
    fun setCurrentActivityLanguage(activity: Activity, locale: String): Configuration {
        val applicationConfiguration = Configuration()
        if (locale == LOCALE_LANG_ENG) {
            Locale.setDefault(Locale.ENGLISH)
            applicationConfiguration.locale = Locale.ENGLISH
        } else {
            Locale.setDefault(Locale.forLanguageTag(LOCALE_LANG_UA))
            applicationConfiguration.locale = Locale.forLanguageTag(LOCALE_LANG_UA)
        }
        activity.resources.updateConfiguration(
            applicationConfiguration,
            activity.resources.displayMetrics
        )
        return applicationConfiguration
    }
}
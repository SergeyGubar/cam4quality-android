package io.github.cam4quality.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import io.github.cam4quality.R
import io.github.cam4quality.ui.BaseFragment
import io.github.cam4quality.ui.login.LoginActivity
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.helper.LocaleHelper
import io.github.cam4quality.utility.helper.SharedPrefHelper
import org.jetbrains.anko.support.v4.intentFor
import org.koin.android.ext.android.inject

class SettingsFragment : BaseFragment() {

    companion object {
        const val ACTION_LOCALE_CHANGED = "ACTION_LOCALE_CHANGED"
        fun newInstance() = SettingsFragment()
    }

    override val layout: Int = R.layout.fragment_settings

    private val uaButton by lazyBind<Button>(R.id.settings_ua_button)
    private val engButton by lazyBind<Button>(R.id.settings_eng_button)
    private val logoutButton by lazyBind<Button>(R.id.settings_logout_button)
    private val prefHelper: SharedPrefHelper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uaButton.setOnClickListener {
            prefHelper.setLanguage(LocaleHelper.LOCALE_LANG_UA)
            LocaleHelper.changeApplicationLocale(activity!!, LocaleHelper.LOCALE_LANG_UA)
            activity?.sendBroadcast(Intent(ACTION_LOCALE_CHANGED))
        }
        engButton.setOnClickListener {
            prefHelper.setLanguage(LocaleHelper.LOCALE_LANG_ENG)
            LocaleHelper.changeApplicationLocale(activity!!, LocaleHelper.LOCALE_LANG_ENG)
            activity?.sendBroadcast(Intent(ACTION_LOCALE_CHANGED))
        }
        logoutButton.setOnClickListener {
            with(prefHelper) {
                setUserLoggedIn(false)
                saveToken("")
                saveLogin("")
                savePassword("")
                startActivity(intentFor<LoginActivity>())
            }
            activity!!.finish()
        }
    }
}
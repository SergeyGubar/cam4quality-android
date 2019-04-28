package io.github.cam4quality.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import io.github.cam4quality.R
import io.github.cam4quality.ui.BaseFragment
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.helper.LocaleHelper
import io.github.cam4quality.utility.helper.SharedPrefHelper
import org.koin.android.ext.android.inject

class SettingsFragment : BaseFragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override val layout: Int = R.layout.fragment_settings

    private val uaButton by lazyBind<Button>(R.id.settings_ua_button)
    private val prefHelper: SharedPrefHelper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uaButton.setOnClickListener {
            prefHelper.setLanguage(LocaleHelper.LOCALE_LANG_UA)
            LocaleHelper.changeApplicationLocale(activity!!, LocaleHelper.LOCALE_LANG_UA)
            activity?.sendBroadcast(Intent("asd"))
        }
    }
}
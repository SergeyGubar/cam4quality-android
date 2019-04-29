package io.github.cam4quality.ui.statistic

import android.os.Bundle
import android.view.View
import android.widget.Button
import io.github.cam4quality.R
import io.github.cam4quality.ui.BaseFragment
import io.github.cam4quality.ui.statistic.interval.FactoryStatisticInIntervalDialogFragment
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.notNullFragmentManager

class FactoriesStatisticFragment: BaseFragment() {

    companion object {
        fun newInstance() = FactoriesStatisticFragment()
    }

    override val layout: Int = R.layout.fragment_statistics

    private val factoriesStatisticsButton by lazyBind<Button>(R.id.statistics_factories)
    private val mostDangerousButton by lazyBind<Button>(R.id.statistics_most_dangerous)
    private val failDailyButton by lazyBind<Button>(R.id.statistics_fail_daily)
    private val factoryAllTimeButton by lazyBind<Button>(R.id.statistics_factory_all_time)
    private val factoryIntervalButton by lazyBind<Button>(R.id.statistics_in_interval)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        factoryIntervalButton.setOnClickListener { FactoryStatisticInIntervalDialogFragment.newInstance().show(notNullFragmentManager, null) }
    }
}
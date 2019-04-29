package io.github.cam4quality.ui.statistic.factoryalltime

import android.os.Bundle
import android.view.View
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesStatisticsRepository
import io.github.cam4quality.ui.BaseFullScreenDialogFragment
import org.koin.android.ext.android.inject

class FactoryAllTimeStatisticsDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance() = FactoryAllTimeStatisticsDialogFragment()
    }

    override val layout: Int = R.layout.dialog_factory_all_time_statistics
    private val repository: FactoriesStatisticsRepository by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
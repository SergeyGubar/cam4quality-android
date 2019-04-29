package io.github.cam4quality.ui.statistic.factories

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesStatisticsRepository
import io.github.cam4quality.ui.BaseFullScreenDialogFragment
import io.github.cam4quality.utility.extension.defaultSetup
import io.github.cam4quality.utility.extension.lazyBind
import org.koin.android.ext.android.inject

class AllFactoriesStatisticsDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance() = AllFactoriesStatisticsDialogFragment()
    }

    override val layout: Int = R.layout.dialog_factories_statistics
    private val recycler by lazyBind<RecyclerView>(R.id.factories_all_time_statistics_recycler)
    private val adapter = AllFactoriesAdapter()
    private val repository: FactoriesStatisticsRepository by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.defaultSetup(adapter)
    }
}
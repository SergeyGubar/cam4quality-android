package io.github.cam4quality.ui.statistic.mostdangerous

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesStatisticsRepository
import io.github.cam4quality.ui.BaseFullScreenDialogFragment
import io.github.cam4quality.utility.extension.defaultSetup
import io.github.cam4quality.utility.extension.lazyBind
import org.koin.android.ext.android.inject

class MostDangerousFactoriesDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance() = MostDangerousFactoriesDialogFragment()
    }

    override val layout: Int = R.layout.dialog_most_dangerous_factories
    private val repository: FactoriesStatisticsRepository by inject()
    private val recycler by lazyBind<RecyclerView>(R.id.most_dangerous_factories_recycler)
    private val adapter = MostDangerousFactoriesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.defaultSetup(adapter)
    }
}
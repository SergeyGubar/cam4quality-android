package io.github.cam4quality.ui.statistic.faildaily

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jaredrummler.materialspinner.MaterialSpinner
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesStatisticsRepository
import io.github.cam4quality.ui.BaseFullScreenDialogFragment
import io.github.cam4quality.utility.extension.defaultSetup
import io.github.cam4quality.utility.extension.lazyBind
import org.koin.android.ext.android.inject

class FactoryFailDailyDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance() = FactoryFailDailyDialogFragment()
    }

    override val layout: Int = R.layout.dialog_factory_fail_daily
    private val recycler by lazyBind<RecyclerView>(R.id.factory_fail_daily_recycler)
    private val spinner by lazyBind<MaterialSpinner>(R.id.factory_fail_daily_spinner)
    private val repository: FactoriesStatisticsRepository by inject()
    private val adapter = FactoryFailDailyAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.defaultSetup(adapter)
    }
}
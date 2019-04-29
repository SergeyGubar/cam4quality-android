package io.github.cam4quality.ui.statistic.factories

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesStatisticsRepository
import io.github.cam4quality.ui.BaseFullScreenDialogFragment
import io.github.cam4quality.utility.extension.addToContainer
import io.github.cam4quality.utility.extension.defaultSetup
import io.github.cam4quality.utility.extension.lazyBind
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class AllFactoriesStatisticsDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance() = AllFactoriesStatisticsDialogFragment()
    }

    override val layout: Int = R.layout.dialog_factories_statistics
    private val recycler by lazyBind<RecyclerView>(R.id.factories_all_time_statistics_recycler)
    private val toolbar by lazyBind<Toolbar>(R.id.factories_all_time_statistics_toolbar)
    private val adapter = AllFactoriesAdapter()
    private val statisticsRepository: FactoriesStatisticsRepository by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.defaultSetup(adapter)
        loadData()
        toolbar.setNavigationOnClickListener { dismiss() }
    }

    private fun loadData() {
        statisticsRepository.getAllFactoriesStatistics()
            .subscribeBy(
                onError = { Timber.w("error ${it.localizedMessage}".also { toast("Error loading data!") }) },
                onSuccess = { result ->
                    result.fold(
                        { response ->
                            Timber.d("success $response")
                            adapter.swapData(response)
                        },
                        { Timber.w("error ${it.localizedMessage}".also { toast("Error loading data!") }) }
                    )
                }
            ).addToContainer(compositeDisposable)
    }
}
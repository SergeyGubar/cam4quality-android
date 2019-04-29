package io.github.cam4quality.ui.statistic.faildaily

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.jaredrummler.materialspinner.MaterialSpinner
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesRepository
import io.github.cam4quality.network.repository.FactoriesStatisticsRepository
import io.github.cam4quality.ui.BaseFullScreenDialogFragment
import io.github.cam4quality.utility.extension.addToContainer
import io.github.cam4quality.utility.extension.defaultSetup
import io.github.cam4quality.utility.extension.lazyBind
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class FactoryFailDailyDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance() = FactoryFailDailyDialogFragment()
    }

    override val layout: Int = R.layout.dialog_factory_fail_daily

    private val toolbar by lazyBind<Toolbar>(R.id.factory_fail_daily_toolbar)
    private val recycler by lazyBind<RecyclerView>(R.id.factory_fail_daily_recycler)
    private val spinner by lazyBind<MaterialSpinner>(R.id.factory_fail_daily_spinner)

    private val statisticsRepository: FactoriesStatisticsRepository by inject()
    private val factoriesRepository: FactoriesRepository by inject()
    private val adapter = FactoryFailDailyAdapter()
    private val factoriesIds: MutableList<String> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.defaultSetup(adapter)
        loadFactoriesData()
        with(toolbar) {
            setNavigationOnClickListener { dismiss() }
        }
    }

    private fun loadFactoriesData() {
        factoriesRepository.getAllFactories()
            .subscribeBy(
                onError = { Timber.w("error: ${it.localizedMessage}").also { toast("Error loading factories info!") } },
                onSuccess = { result ->
                    result.fold(
                        { response ->
                            Timber.d("success: $response")
                            factoriesIds.addAll(response.map { it.id })
                            spinner.setItems(factoriesIds)
                            spinner.setOnItemSelectedListener { _, position, _, _ ->
                                loadFactoryStatistics(factoriesIds[position])
                            }
                        },
                        { Timber.w("error: ${it.localizedMessage}").also { toast("Error loading factories info!") } }
                    )
                }
            )
            .addToContainer(compositeDisposable)
    }

    private fun loadFactoryStatistics(factoryId: String) {
        statisticsRepository.getFactoryFailDailyStatistics(factoryId)
            .subscribeBy(
                onError = { Timber.w("error: ${it.localizedMessage}".also { toast("Error loading statistics!") }) },
                onSuccess = { result ->
                    result.fold(
                        { response ->
                            Timber.d("success: $response")
                            adapter.swapData(response)
                        },
                        { Timber.w("error: ${it.localizedMessage}".also { toast("Error loading statistics!") }) }
                    )
                }
            )
            .addToContainer(compositeDisposable)
    }
}
package io.github.cam4quality.ui.statistic.factoryalltime

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.jaredrummler.materialspinner.MaterialSpinner
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesRepository
import io.github.cam4quality.network.repository.FactoriesStatisticsRepository
import io.github.cam4quality.ui.BaseFullScreenDialogFragment
import io.github.cam4quality.utility.extension.addToContainer
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.toFixedNumberString
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class FactoryAllTimeStatisticsDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance() = FactoryAllTimeStatisticsDialogFragment()
    }

    override val layout: Int = R.layout.dialog_factory_all_time_statistics

    private val toolbar by lazyBind<Toolbar>(R.id.factory_all_time_statistics_toolbar)
    private val factoriesSpinner by lazyBind<MaterialSpinner>(R.id.factory_all_time_statistics_spinner)
    private val nameTextView by lazyBind<TextView>(R.id.factory_all_time_name_value)
    private val countTextView by lazyBind<TextView>(R.id.factory_all_time_details_count_value)
    private val failedCountTextView by lazyBind<TextView>(R.id.factory_all_time_failed_details_count_value)
    private val failedPercentTextView by lazyBind<TextView>(R.id.factory_all_time_failed_percent_value)
    private val statisticsRepository: FactoriesStatisticsRepository by inject()
    private val factoriesRepository: FactoriesRepository by inject()
    private val factoriesIds: MutableList<String> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(toolbar) {
            setNavigationOnClickListener { dismiss() }
        }
        loadFactoriesData()
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
                            factoriesSpinner.setItems(factoriesIds)
                            factoriesSpinner.setOnItemSelectedListener { _, position, _, _ ->
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
        statisticsRepository.getFactoryAllTimeStatistics(factoryId)
            .subscribeBy(
                onError = { Timber.w("error: ${it.localizedMessage}".also { toast("Error loading statistics!") }) },
                onSuccess = { result ->
                    result.fold(
                        { response ->
                            Timber.d("success: $response")
                            with(response) {
                                nameTextView.text = factoryName
                                countTextView.text = allDetailsCount.toString()
                                failedCountTextView.text = failedDetailsCount.toString()
                                failedPercentTextView.text = failedPercent.toFixedNumberString(4)
                            }
                        },
                        { Timber.w("error: ${it.localizedMessage}".also { toast("Error loading statistics!") }) }
                    )
                }
            )
            .addToContainer(compositeDisposable)
    }
}
package io.github.cam4quality.ui.statistic.interval

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
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
import io.github.cam4quality.utility.extension.notNullContext
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class FactoryStatisticInIntervalDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance() = FactoryStatisticInIntervalDialogFragment()
    }

    override val layout: Int = R.layout.dialog_statistics_in_interval
    private val statisticsRepository: FactoriesStatisticsRepository by inject()
    private val factoriesRepository: FactoriesRepository by inject()

    private val startTextView by lazyBind<TextView>(R.id.factory_statistics_in_interval_start)
    private val endTextView by lazyBind<TextView>(R.id.factory_statistics_in_interval_end)
    private val toolbar by lazyBind<Toolbar>(R.id.factory_statistics_in_interval_toolbar)
    private val generateButton by lazyBind<Button>(R.id.factory_statistics_in_interval_generate_button)
    private val factoriesSpinner by lazyBind<MaterialSpinner>(R.id.factory_statistics_in_interval_spinner)
    private val allDetailsCountTextView by lazyBind<TextView>(R.id.factory_statistics_in_interval_all_details_value)
    private val factoryNameTextView by lazyBind<TextView>(R.id.factory_statistics_in_interval_factory_name_value)
    private val failedCountTextView by lazyBind<TextView>(R.id.factory_statistics_in_interval_failed_count_value)
    private val failedPercentTextView by lazyBind<TextView>(R.id.factory_statistics_in_interval_failed_percent_value)

    private val factoriesIds: MutableList<String> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFactoriesIds()
        setupListeners()
        with(toolbar) {
            setNavigationOnClickListener { dismiss() }
        }
    }

    private fun setupListeners() {
        val startCalendar = Calendar.getInstance()
        val endCalendar = Calendar.getInstance()

        val onDateSetStartListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            with(startCalendar) {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            updateDateLabel(startTextView, startCalendar.time)
        }

        val onDateSetEndListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            with(endCalendar) {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            updateDateLabel(endTextView, endCalendar.time)
        }
        startTextView.setOnClickListener {
            DatePickerDialog(
                notNullContext,
                onDateSetStartListener,
                startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        endTextView.setOnClickListener {
            DatePickerDialog(
                notNullContext,
                onDateSetEndListener,
                endCalendar.get(Calendar.YEAR),
                endCalendar.get(Calendar.MONTH),
                endCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        generateButton.setOnClickListener {
            loadStatistics(
                factoriesIds[factoriesSpinner.selectedIndex],
                startTextView.text.toString(),
                endTextView.text.toString()
            )
        }
    }

    private fun loadFactoriesIds() {
        factoriesRepository
            .getAllFactories()
            .subscribeBy(
                onError = { Timber.w("Error: ${it.localizedMessage}".also { toast("Failed loading factories info!") }) },
                onSuccess = { result ->
                    result.fold(
                        { response ->
                            factoriesIds.addAll(response.map { it.id })
                            factoriesSpinner.setItems(factoriesIds)
                        },
                        { Timber.w("Error: ${it.localizedMessage}".also { toast("Failed loading factories info!") }) }
                    )
                }
            )
            .addToContainer(compositeDisposable)
    }

    private fun loadStatistics(factoryId: String, start: String, end: String) {
        statisticsRepository.getFactoryStatisticsInInterval(start, end, factoryId)
            .subscribeBy(
                onError = { Timber.w("error: ${it.localizedMessage}".also { toast("Error loading statistics!") }) },
                onSuccess = { result ->
                    result.fold(
                        onSuccess = { response ->
                            Timber.d("success: $response")
                            with(response) {
                                allDetailsCountTextView.text = allDetailsCount.toString()
                                factoryNameTextView.text = factoryName
                                failedCountTextView.text = failedDetailsCount.toString()
                                failedPercentTextView.text = failedPercent
                            }
                        },
                        onFailure = { Timber.w("error: ${it.localizedMessage}".also { toast("Error loading statistics!") }) }
                    )
                }
            ).addToContainer(compositeDisposable)
    }

    private fun updateDateLabel(textView: TextView, date: Date) {
        textView.text = SimpleDateFormat("MM/dd/yyyy", Locale.US).format(date)
    }
}
package io.github.cam4quality.ui.statistic.mostdangerous

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

class MostDangerousFactoriesDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance() = MostDangerousFactoriesDialogFragment()
    }

    override val layout: Int = R.layout.dialog_most_dangerous_factories
    private val repository: FactoriesStatisticsRepository by inject()
    private val recycler by lazyBind<RecyclerView>(R.id.most_dangerous_factories_recycler)
    private val toolbar by lazyBind<Toolbar>(R.id.most_dangerous_factories_toolbar)
    private val adapter = MostDangerousFactoriesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.defaultSetup(adapter)
        loadData()
        with(toolbar) {
            setNavigationOnClickListener { dismiss() }
        }
    }

    private fun loadData() {
        repository.getMostDangerousFactories()
            .subscribeBy(
                onError = { Timber.w("error: ${it.localizedMessage}".also { toast("Error loading statistics!") }) },
                onSuccess = { result ->
                    result.fold(
                        { response ->
                            Timber.d("success $response")
                            adapter.swapData(response)
                        },
                        { Timber.w("error: ${it.localizedMessage}".also { toast("Error loading statistics!") }) }
                    )
                }
            )
            .addToContainer(compositeDisposable)
    }
}
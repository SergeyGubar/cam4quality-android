package io.github.cam4quality.ui.deviation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.QualityParamDeviationResponseModel
import io.github.cam4quality.network.repository.QualityParamsDeviationsRepository
import io.github.cam4quality.ui.BaseFragment
import io.github.cam4quality.utility.extension.addToContainer
import io.github.cam4quality.utility.extension.defaultSetup
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.notNullFragmentManager
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class QualityParamsDeviationsFragment : BaseFragment() {

    companion object {
        fun newInstance() = QualityParamsDeviationsFragment()
    }

    override val layout: Int = R.layout.fragment_quality_params_deviations
    private val recycler by lazyBind<RecyclerView>(R.id.quality_params_deviations_recycler)
    private val fab by lazyBind<FloatingActionButton>(R.id.quality_params_deviations_fab)
    private val repository: QualityParamsDeviationsRepository by inject()
    private val adapter = QualityParamsDeviationsAdapter(::onDeviationClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.defaultSetup(adapter)
        fab.setOnClickListener {
            AddQualityParamDeviationDialogFragment.newInstance(::loadData).show(notNullFragmentManager, null)
        }
        loadData()
    }

    private fun loadData() {
        repository.getAllQualityParamsDeviations()
            .subscribeBy(
                onError = { Timber.d("error: ${it.localizedMessage}".also { toast("Failed loading quality params deviations!") }) },
                onSuccess = { result ->
                    result.fold(
                        { response ->
                            Timber.d("success: $response")
                            adapter.swapData(response)
                        },
                        { Timber.d("error: ${it.localizedMessage}".also { toast("Failed loading quality params deviations!") }) }
                    )
                }
            ).addToContainer(compositeDisposable)
    }

    private fun onDeviationClick(deviation: QualityParamDeviationResponseModel) {
        repository.removeQualityParamDeviation(deviation.id)
            .subscribeBy(
                onError = { Timber.d("error: ${it.localizedMessage}".also { toast("Error removing param deviation!") }) },
                onSuccess = {
                    Timber.d("success")
                    adapter.removeItem(deviation.id)
                    toast("Removed ${deviation.id}")
                }
            ).addToContainer(compositeDisposable)
    }
}
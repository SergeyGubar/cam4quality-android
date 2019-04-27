package io.github.cam4quality.ui.qualityparam

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.QualityParamResponseModel
import io.github.cam4quality.network.repository.QualityParamsRepository
import io.github.cam4quality.ui.BaseFragment
import io.github.cam4quality.utility.extension.*
import io.github.cam4quality.utility.helper.DialogUtil
import io.github.cam4quality.utility.helper.QualityParamDeviationCalculator
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class QualityParamsFragment : BaseFragment() {

    companion object {
        fun newInstance() = QualityParamsFragment()
    }

    override val layout: Int = R.layout.fragment_quality_params

    private val recycler by lazyBind<RecyclerView>(R.id.quality_params_recycler)
    private val fab by lazyBind<FloatingActionButton>(R.id.quality_params_fab)
    private val qualityParamsAdapter = QualityParamsAdapter(::onQualityParamClick)
    private val qualityParamsRepository: QualityParamsRepository by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupListeners()
        handler.postDelayed({ loadQualityParams() }, 500)
    }

    private fun setupRecycler() {
        recycler.defaultSetup(qualityParamsAdapter)
    }

    private fun setupListeners() {
        fab.setOnClickListener {
            AddQualityParamDialogFragment.newInstance(::loadQualityParams).show(notNullFragmentManager, null)
        }
    }

    private fun loadQualityParams() {
        qualityParamsRepository
            .getAllQualityParams()
            .subscribeBy(
                onError = { Timber.d("error $it") },
                onSuccess = { result ->
                    result.fold(
                        { response ->
                            qualityParamsAdapter.swapData(response)
                        },
                        { Timber.w("error: $it"); toast(getString(R.string.error_loading_quality_params)) }
                    )
                }
            )
            .addToContainer(compositeDisposable)
    }

    private fun onQualityParamClick(qualityParam: QualityParamResponseModel) {
        with(qualityParam) {
            DialogUtil.showQualityParamDialog(
                notNullContext,
                value,
                paramDeviation.normalValue,
                paramDeviation.maxValueDeviation,
                paramDeviation.minValueDeviation,
                QualityParamDeviationCalculator.calculacteDeviationPercent(
                    paramDeviation.normalValue, value
                )
            )
        }
    }
}
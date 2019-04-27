package io.github.cam4quality.ui.qualityparam

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.jaredrummler.materialspinner.MaterialSpinner
import io.github.cam4quality.R
import io.github.cam4quality.network.entity.response.QualityParamDeviationResponseModel
import io.github.cam4quality.network.repository.QualityParamsDeviationsRepository
import io.github.cam4quality.network.repository.QualityParamsRepository
import io.github.cam4quality.ui.BaseFullScreenDialogFragment
import io.github.cam4quality.utility.extension.addToContainer
import io.github.cam4quality.utility.extension.input
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.toFixedNumberString
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class AddQualityParamDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance(saveCallback: () -> Unit) = AddQualityParamDialogFragment().apply {
            this.onSave = saveCallback
        }
    }

    override val layout: Int = R.layout.dialog_add_quality_param

    private val nameEditText by lazyBind<EditText>(R.id.add_quality_param_name_edit_text)
    private val valueEditText by lazyBind<EditText>(R.id.add_quality_param_value_edit_text)
    private val toolbar by lazyBind<Toolbar>(R.id.add_quality_param_toolbar)
    private val deviationSpinner by lazyBind<MaterialSpinner>(R.id.add_quality_param_deviation_spinner)
    private val normalDeviationValueTextView by lazyBind<TextView>(R.id.add_quality_param_normal_deviation_value)
    private val maxDeviationValueTextView by lazyBind<TextView>(R.id.add_quality_param_max_deviation_value)
    private val minDeviationValueTextView by lazyBind<TextView>(R.id.add_quality_param_min_deviation_value)

    private val deviationsRepository: QualityParamsDeviationsRepository by inject()
    private val qualityParamsRepository: QualityParamsRepository by inject()

    private val qualityParamsDeviations: MutableList<QualityParamDeviationResponseModel> = mutableListOf()
    private var onSave: (() -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(toolbar) {
            setOnMenuItemClickListener { handleMenuItemClick(it.itemId) }
            setNavigationOnClickListener { dismiss() }
            inflateMenu(R.menu.menu_save_icon)
        }
        loadDeviationsData()
    }

    private fun handleMenuItemClick(id: Int): Boolean {
        return when (id) {
            R.id.menu_action_save -> {
                addQualityParam()
                true
            }
            else -> false
        }
    }

    private fun addQualityParam() {
        qualityParamsRepository.addQualityParam(
            nameEditText.input,
            qualityParamsDeviations[deviationSpinner.selectedIndex].id,
            valueEditText.input.toDouble()
        ).subscribeBy(
            onError = { Timber.w("error: $it".also { toast("Failed adding param!") }) },
            onSuccess = { onSave?.invoke(); dismiss() }
        ).addToContainer(compositeDisposable)
    }

    private fun loadDeviationsData() {
        deviationsRepository.getAllQualityParamsDeviations()
            .subscribeBy(
                onError = { err -> Timber.w("error: ${err.localizedMessage}") },
                onSuccess = { result ->
                    result.fold(
                        { response ->
                            Timber.d("success: $response")
                            qualityParamsDeviations.addAll(response)
                            initSpinner(response.map { it.id })
                        },
                        { err -> Timber.w("error: ${err.localizedMessage}") }
                    )
                }
            ).addToContainer(compositeDisposable)
    }

    private fun initSpinner(ids: List<String>) {
        with(deviationSpinner) {
            setItems(ids)
            setOnItemSelectedListener { view, position, id, item ->
                val deviation = qualityParamsDeviations[position]
                normalDeviationValueTextView.text = deviation.normalValue.toFixedNumberString(4)
                maxDeviationValueTextView.text = deviation.maxValueDeviation.toFixedNumberString(4)
                minDeviationValueTextView.text = deviation.minValueDeviation.toFixedNumberString(4)
            }
        }
    }
}
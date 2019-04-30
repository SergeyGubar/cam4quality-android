package io.github.cam4quality.ui.deviation

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.QualityParamsDeviationsRepository
import io.github.cam4quality.ui.BaseFullScreenDialogFragment
import io.github.cam4quality.utility.extension.addToContainer
import io.github.cam4quality.utility.extension.input
import io.github.cam4quality.utility.extension.lazyBind
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class AddQualityParamDeviationDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance(onSave: () -> Unit) = AddQualityParamDeviationDialogFragment().apply {
            this.onDataSaved = onSave
        }
    }

    override val layout: Int = R.layout.dialog_add_quality_param_deviation
    private val repository: QualityParamsDeviationsRepository by inject()
    private val normalValueEditText by lazyBind<EditText>(R.id.add_quality_param_deviation_normal_value_edit_text)
    private val maxDeviationEditText by lazyBind<EditText>(R.id.add_quality_param_deviation_max_value_edit_text)
    private val minDeviationEditText by lazyBind<EditText>(R.id.add_quality_param_deviation_min_value_edit_text)
    private val descriptionEditText by lazyBind<EditText>(R.id.add_quality_param_deviation_description_edit_text)
    private val toolbar by lazyBind<Toolbar>(R.id.add_quality_param_deviation_toolbar)
    private var onDataSaved: (() -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(toolbar) {
            setOnMenuItemClickListener { handleMenuItemClick(it.itemId) }
            setNavigationOnClickListener { dismiss() }
            inflateMenu(R.menu.menu_save_icon)
        }
    }

    private fun handleMenuItemClick(id: Int): Boolean {
        return when (id) {
            R.id.menu_action_save -> {
                addQualityParamDeviation()
                true
            }
            else -> false
        }
    }

    private fun addQualityParamDeviation() {
        repository.addQualityParamDeviation(
            descriptionEditText.input,
            maxDeviationEditText.input.toDouble(),
            minDeviationEditText.input.toDouble(),
            normalValueEditText.input.toDouble()
        ).subscribeBy(
            onError = { Timber.w("error: ${it.localizedMessage}".also { toast("Error adding deviation!") }) },
            onSuccess = { onDataSaved?.invoke(); dismiss() }
        ).addToContainer(compositeDisposable)
    }
}
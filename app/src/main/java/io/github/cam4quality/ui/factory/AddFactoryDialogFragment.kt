package io.github.cam4quality.ui.factory

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesRepository
import io.github.cam4quality.ui.BaseFullScreenDialogFragment
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.input
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber


class AddFactoryDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance(onSave: () -> Unit): AddFactoryDialogFragment {
            return AddFactoryDialogFragment().apply {
                onDataSaved = onSave
            }
        }
    }

    override val layout: Int = R.layout.dialog_add_factory

    private val toolbar by lazyBind<Toolbar>(R.id.add_factory_toolbar)
    private val nameEditText by lazyBind<EditText>(R.id.add_factory_name_edit_text)
    private val typeEditText by lazyBind<EditText>(R.id.add_factory_type_edit_text)
    private val addressEditText by lazyBind<EditText>(R.id.add_factory_address_edit_text)
    private val factoriesRepository: FactoriesRepository by inject()
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
                addFactory()
                true
            }
            else -> false
        }
    }

    private fun addFactory() {
        val addFactoryObservable = factoriesRepository.addFactory(
            nameEditText.input,
            typeEditText.input,
            addressEditText.input
        )
        compositeDisposable.add(
            addFactoryObservable.subscribeBy(
                onError = { err ->
                    Timber.w("error: ${err.localizedMessage}")
                    toast(getString(R.string.error_adding_factory))
                },
                onSuccess = { response ->
                    Timber.d("$response")
                    onDataSaved?.invoke()
                    dismiss()
                }
            )
        )
    }
}
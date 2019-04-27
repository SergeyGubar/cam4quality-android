package io.github.cam4quality.ui.user

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.jaredrummler.materialspinner.MaterialSpinner
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.FactoriesRepository
import io.github.cam4quality.network.repository.UsersRepository
import io.github.cam4quality.ui.BaseFullScreenDialogFragment
import io.github.cam4quality.utility.extension.lazyBind
import io.github.cam4quality.utility.extension.input
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class AddUserDialogFragment : BaseFullScreenDialogFragment() {

    companion object {
        fun newInstance(onSave: () -> Unit): AddUserDialogFragment {
            return AddUserDialogFragment().apply {
                this.onSave = onSave
            }
        }
    }

    override val layout: Int = R.layout.dialog_add_user

    private val toolbar by lazyBind<Toolbar>(R.id.add_user_toolbar)
    private val factoriesSpinner by lazyBind<MaterialSpinner>(R.id.add_user_factories_spinner)
    private val userNameEditText by lazyBind<EditText>(R.id.add_user_username_edit_text)
    private val emailEditText by lazyBind<EditText>(R.id.add_user_email_edit_text)
    private val passwordEditText by lazyBind<EditText>(R.id.add_user_password_edit_text)

    private var onSave: (() -> Unit)? = null

    private val factoriesRepository: FactoriesRepository by inject()
    private val usersRepository: UsersRepository by inject()
    private val factoriesIds: MutableList<String> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(toolbar) {
            setOnMenuItemClickListener { handleMenuItemClick(it.itemId) }
            setNavigationOnClickListener { dismiss() }
            inflateMenu(R.menu.menu_save_icon)
        }
        loadFactoriesInfo()
    }

    private fun handleMenuItemClick(id: Int): Boolean {
        return when (id) {
            R.id.menu_action_save -> {
                registerUser()
                true
            }
            else -> false
        }
    }

    private fun registerUser() {
        compositeDisposable.add(usersRepository.register(
            userNameEditText.input,
            emailEditText.input,
            passwordEditText.input,
            factoriesIds[factoriesSpinner.selectedIndex]
        ).subscribeBy(
            onError = { err -> Timber.w("error: ${err.localizedMessage}") },
            onSuccess = {
                onSave?.invoke()
                dismiss()
            }
        ))
    }

    private fun loadFactoriesInfo() {
        compositeDisposable.add(
            factoriesRepository.getAllFactories().subscribeBy(
                onSuccess = { response ->
                    response.fold(
                        { result ->
                            Timber.d("success")
                            val factoriesIds = result.map { it.id }
                            this.factoriesIds.addAll(factoriesIds)
                            initSpinner(factoriesIds)
                        },
                        { err ->
                            Timber.w("error: ${err.localizedMessage}")
                            toast(getString(R.string.error_loading_factories_data))
                        }
                    )
                },
                onError = { Timber.w("error ${it.localizedMessage}") }
            )
        )
    }

    private fun initSpinner(data: List<String>) {
        Timber.d("initSpinner: data = [$data]")
        factoriesSpinner.setItems(data)
    }
}
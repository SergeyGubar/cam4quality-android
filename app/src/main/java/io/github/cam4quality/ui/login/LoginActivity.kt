package io.github.cam4quality.ui.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import io.github.cam4quality.BaseActivity
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.LoginRepository
import io.github.cam4quality.ui.main.MainActivity
import io.github.cam4quality.utility.extension.addSimpleTextChangedListener
import io.github.cam4quality.utility.extension.bind
import io.github.cam4quality.utility.extension.hideError
import io.github.cam4quality.utility.extension.input
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.github.cam4quality.utility.validator.CredentialsValidator
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class LoginActivity : BaseActivity() {

    private val loginRepository: LoginRepository by inject()
    private val prefHelper: SharedPrefHelper by inject()
    private val nextButton by bind<Button>(R.id.login_next_button)
    private val emailEditText by bind<EditText>(R.id.login_email_edit_text)
    private val passwordEditText by bind<EditText>(R.id.login_password_edit_text)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (prefHelper.getToken().isNotBlank()) {
            startMainActivity()
        }
        setupListeners()
    }

    private fun setupListeners() {
        nextButton.setOnClickListener {
            validateInput {
                login(emailEditText.input, passwordEditText.input)
            }
        }
        emailEditText.addSimpleTextChangedListener { emailEditText.hideError() }
        passwordEditText.addSimpleTextChangedListener { passwordEditText.hideError() }
    }

    private fun validateInput(onValid: () -> Unit) {
        val isEmailValid = CredentialsValidator.isEmailValid(emailEditText.input)
        val isPasswordValid = CredentialsValidator.isPasswordValid(passwordEditText.input)
        if (!isEmailValid) emailEditText.error = getString(R.string.error_email)
        if (!isPasswordValid) passwordEditText.error = getString(R.string.error_password)
        if (!isEmailValid || !isPasswordValid) return
        onValid()
    }

    private fun login(email: String, password: String) {
        Timber.d("login: email = [$email], password = [$password]")
        compositeDisposable.add(
            loginRepository.login(email, password)
                .subscribeBy(
                    onError = {
                        Timber.w("Login failed ${it.localizedMessage}")
                        toast("Login failed!")
                    },
                    onSuccess = { result ->
                        result.fold({ loginResponse ->
                            Timber.d("Login success $loginResponse")
                            // Security actually cries here, but i've got no time to implement better mechanism
                            with(prefHelper) {
                                saveLogin(email)
                                savePassword(password)
                                saveToken(loginResponse?.token)
                            }.also {
                                startMainActivity()
                            }

                        }, { err ->
                            Timber.w("Login failed ${err.localizedMessage}")
                            toast("Login failed!")
                        })
                    }
                )
        )
    }

    private fun startMainActivity() {
        startActivity(intentFor<MainActivity>()); finish()
    }
}

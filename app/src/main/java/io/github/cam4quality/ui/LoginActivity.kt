package io.github.cam4quality.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import io.github.cam4quality.BaseActivity
import io.github.cam4quality.R
import io.github.cam4quality.network.repository.LoginRepository
import io.github.cam4quality.utility.extension.addSimpleTextChangedListener
import io.github.cam4quality.utility.extension.bind
import io.github.cam4quality.utility.extension.hideError
import io.github.cam4quality.utility.extension.input
import io.github.cam4quality.utility.validator.CredentialsValidator
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity() {

    private val loginRepository: LoginRepository by inject()

    private val nextButton by bind<Button>(R.id.login_next_button)
    private val emailEditText by bind<EditText>(R.id.login_email_edit_text)
    private val passwordEditText by bind<EditText>(R.id.login_password_edit_text)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupListeners()
    }

    private fun setupListeners() {
        nextButton.setOnClickListener { validateInput { startActivity(intentFor<MainActivity>()); finish() } }
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
}

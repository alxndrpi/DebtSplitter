package com.example.debtsplitter

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo

import android.widget.Button
import android.widget.Toast

import android.widget.EditText
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.debtsplitter.ui.login.LoggedInUserView
import com.example.debtsplitter.ui.login.LoginViewModel
import com.example.debtsplitter.ui.login.LoginViewModelFactory
import com.example.debtsplitter.ui.login.afterTextChanged

class Activity_login2 : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        val password = findViewById<EditText>(R.id.pass)
        val login = findViewById<Button>(R.id.login2)

        // TODO:

        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@Activity_login2, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            //if (loginState.usernameError != null) {
            //    username.error = getString(loginState.usernameError)
            //}
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@Activity_login2, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)              // write fun
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)           // write fun
                openInputActivity()                             // write fun
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        password.afterTextChanged {
            loginViewModel.loginDataChanged(
                password.text.toString()
                //password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    password.text.toString()
                    //password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            password.text.toString()
                            //password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(password.text.toString())
            }
        }
    }

    private fun openInputActivity() {
        val intent = Intent(this, Activity_login2::class.java)

        startActivity(intent)
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }



}

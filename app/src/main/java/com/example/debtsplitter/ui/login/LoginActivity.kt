package com.example.debtsplitter.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONObject



import com.example.debtsplitter.R
import com.example.debtsplitter.Activity_login2

class LoginActivity : AppCompatActivity() {


    //class Pattern


    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.tel)


        //val reg = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}\$"
        //var pattern: Pattern = Pattern.compile(reg)
        //fun CharSequence.isPhoneNumber() : Boolean = pattern.matcher(this).find()

        // TODO : send tel to generate password, and wait while user input password in to passText

        //val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            //if (loginState.passwordError != null) {
            //    password.error = getString(loginState.passwordError)
            //}
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
                openInputActivity()
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged2(
                username.text.toString()
                //password.text.toString()
            )
        }

        username.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged2(
                    username.text.toString()
                    //password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login2(
                            username.text.toString()
                            //password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE

                getUser(username.text.toString())
                //Thread.sleep(20_000)

                requestFunPost(username.text.toString())
                //Thread.sleep(20_000)

                loginViewModel.login2(username.text.toString())
            }
        }


    }

    private fun getUser(username_ : String) {
        // Instantiate the RequestQueue.
        val textView5 = findViewById<TextView>(R.id.textViewX)
        val queue = Volley.newRequestQueue(this)
        var url = "http://82.146.39.239:8000/users/send_verification_code/?username="
        url += username_

        /*
        val stringRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                // Display the first 500 characters of the response string.
                textView5!!.text = "Response is: ${response.getJSONObject("Name")}"
            },
            Response.ErrorListener { textView5!!.text = "That didn't work!" })


        // Request a string response from the provided URL.

        val stringRequest = StringRequest(Request.Method.GET, url,
        Response.Listener<String> { response ->
            // Display the first 500 characters of the response string.
            textView.text = "Response is: ${response.substring(0, 500)}"
        },
        Response.ErrorListener { textView.text = "That didn't work!" })


// Add the request to the RequestQueue.


         */

        // Request a string response from the provided URL.

        val stringReq = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->

               // var strResp = response.toString()
               //val jsonObj: JSONObject = JSONObject(strResp)
               // val jsonArray: JSONArray = jsonObj.getJSONArray("items")
                //var str_user: String = ""
                //for (i in 0 until jsonArray.length()) {
                //    var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                //    str_user = str_user + "\n" + jsonInner.get("username")
                //}
                //textView5!!.text = "response : $str_user "

                //Toast.makeText(this, "My_token=${response}", Toast.LENGTH_LONG).show()

            },
            Response.ErrorListener {
                textView5!!.text = "That didn't work!"
                //Toast.makeText(this, "Error=${it.message}", Toast.LENGTH_LONG).show()
            })




        queue.add(stringReq)
    }

    private fun requestFunPost(username_ : String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://82.146.39.239:8000/users/"

        val req = object : JsonObjectRequest(Request.Method.POST, url, null,
            Response.Listener {
                //Toast.makeText(this, "My_token=${it.getString("username")}", Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener {

                //Toast.makeText(this, "Error!!=${it.message}", Toast.LENGTH_LONG).show()

            }
        ) {
            override fun getBody(): ByteArray {
                val my_post_string = "{\n" +
                        "    \"username\":  \"" + username_ + "\",\n" +
                        "}"
                return my_post_string.toByteArray()
            }
        }
        // Add the request to the RequestQueue.
        queue.add(req)
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

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

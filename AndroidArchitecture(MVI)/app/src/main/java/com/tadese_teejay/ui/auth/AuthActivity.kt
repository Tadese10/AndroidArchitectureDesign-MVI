package com.tadese_teejay.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tadese_teejay.R
import com.tadese_teejay.api.common.AppSharedPreference
import com.tadese_teejay.model.auth.UserData
import com.tadese_teejay.ui.auth.state.AuthViewStateEvent
import com.tadese_teejay.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        //Handle logged-in User
        CheckIfUserHasLoggedIn()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    private fun CheckIfUserHasLoggedIn() {
        AppSharedPreference(this).getUserData()?.let {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Observe()
    }

    private fun Observe() {
        viewModel.data.observe(this, Observer { dataState ->
            dataState?.let {
                //Handle loading
                showProgressBar(dataState.loading)

                if (dataState.data?.user == null && !dataState.loading)
                    dataState.message = "No user found!"

                //Handle message
                dataState.message?.let { message ->
                    showToast(message)
                }

            }

            dataState.data?.let { authState ->
                authState.user?.let {
                    //set user data
                    viewModel.setUser(it)
                }
            }


        })

        viewModel.viewState.observe(this, Observer {
            it.user?.let {
                //set user data
                println("DEBUG: Setting user data: ${it}")
                setUserProperties(it)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.data.removeObservers(this)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showProgressBar(loading: Boolean) {
        progress_bar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setUserProperties(userData: UserData) {
        AppSharedPreference(this).saveUserData(userData)
        gotoHomeActivity();
    }

    fun Click(view: View) {
        viewModel.setStateEvent(AuthViewStateEvent.GetUserEvent(username.text.toString()))
    }

    private fun gotoHomeActivity() {
        startActivity(Intent(this@AuthActivity,MainActivity::class.java))
        finish()
    }
}
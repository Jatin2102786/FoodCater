package com.example.emergency.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.example.emergency.R
import com.example.emergency.viewmodels.LoginViewModel

class LoginActivity : BaseActivity(), View.OnClickListener {
    private val mLoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    lateinit var btnLogin: Button
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var pbLogin: ProgressBar

    override fun getContentId(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set Observer
        setObserver()

        // Binding
        btnLogin = findViewById(R.id.btn_login_submit)
        etEmail = findViewById(R.id.et_login_email)
        etPassword = findViewById(R.id.er_login_password)
        pbLogin = findViewById(R.id.pbLogin)

        // Set click listener
        btnLogin.setOnClickListener(this)
    }

    private fun setObserver() {
        // Message observer
        mLoginViewModel.onGetMessage().observe(this) {
            if (it.isNotEmpty()) {
                showToast(it)
            }
        }

        // Progress observer
        mLoginViewModel.onGetProgress().observe(this) {
            if (it) {
                pbLogin.visibility = View.VISIBLE
            } else {
                pbLogin.visibility = View.GONE
            }
        }

        // Login success observer
        mLoginViewModel.onGetLoginSuccess().observe(this) {
            if (it != null) {
                // Set data on intent and go to main activity
                val name = it.fname + " " + it.lname
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name", name)
                gotoActivity(intent)
                finishAffinity()
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_login_submit -> {
                mLoginViewModel.login(etEmail.text.toString(), etPassword.text.toString())
            }
        }
    }
}
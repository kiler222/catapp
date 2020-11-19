package com.kiler.catapp.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.kiler.catapp.R
import com.kiler.catapp.utils.LoginUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        loginButton.setOnClickListener {

            hideKeyboard()


            val loginName = username.text.toString().takeUnless { it.isEmpty() } ?: ""
            val password = password.text.toString().takeUnless { it.isEmpty() } ?: ""


            if (!LoginUtil.validateLoginInput(loginName, password)) {

                Toast.makeText(this, getString(R.string.fill_credentials), Toast.LENGTH_LONG).show()
                return@setOnClickListener

            } else {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }

        }

    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }

}



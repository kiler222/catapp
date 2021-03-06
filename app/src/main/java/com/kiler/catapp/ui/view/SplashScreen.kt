package com.kiler.catapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kiler.catapp.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //there is no checking if user was logged before, so every time the app starts we navigate
        //to the login screen
        val newIntent = Intent(this, LoginActivity::class.java)
        startActivity(newIntent)
        finish()
    }
}
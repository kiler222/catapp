package com.kiler.catapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kiler.catapp.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //TODO change to LoginActivity
        val newIntent = Intent(this, MainActivity::class.java)
        startActivity(newIntent)
        finish()
    }
}
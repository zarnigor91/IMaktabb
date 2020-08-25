package com.example.imaktab.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.imaktab.PREFERENCE
import com.example.imaktab.PREF_PASS
import com.example.imaktab.PREF_USER
import com.example.imaktab.R
import com.example.imaktab.dashboard.DashboardActivity
import com.example.imaktab.login.LoginActivity
import java.util.*

class SplashActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_activity)

        val locale = Locale("uz")
        Locale.setDefault(locale)
        val configuration = this.resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }
        this.resources.updateConfiguration(configuration, this.resources.displayMetrics)


         val intent =    Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        //3second splash time
        Handler().postDelayed({
            //start main activity
            startActivity(intent)
            //finish this activity
            finish()
        },3000)
    }
}
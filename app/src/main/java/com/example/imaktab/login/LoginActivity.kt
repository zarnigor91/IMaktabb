package com.example.imaktab.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.imaktab.*
import com.example.imaktab.dashboard.DashboardActivity
import java.util.*

class LoginActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (isLogIn() != null) {
            Log.d(IMAKTAB,isLogIn())
            val intent = Intent(applicationContext, DashboardActivity::class.java)
            startActivity(intent)
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container2, LoginFragment())
                .commit()
        }
        val prefShar = PreferenceManager.getDefaultSharedPreferences(App.getApplication())
        val lan = prefShar.getString("language", "")
        val locale = Locale(lan)
        Locale.setDefault(locale)
        val resources = this.resources
        val dm = resources.displayMetrics
        val configuration = resources.configuration
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale)
        }else{
            configuration.locale = locale
        }
        resources.updateConfiguration(configuration, dm)
    }
    private fun isLogIn(): String? {
        return PreferenceManager.getDefaultSharedPreferences(App.getApplication())
            .getString(PREF_USER, null)

    }
}
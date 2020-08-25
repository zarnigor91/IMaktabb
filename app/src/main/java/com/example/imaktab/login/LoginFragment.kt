package com.example.imaktab.login

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceManager
import com.example.imaktab.App
import com.example.imaktab.BaseFragment
import com.example.imaktab.PREF_USER
import com.example.imaktab.R
import com.example.imaktab.dashboard.DashboardActivity
import com.example.imaktab.verification.VerificationFragment
import com.example.imaktab.verification.VerificationModel
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.login_fragment.*

import java.util.*


class LoginFragment : BaseFragment(R.layout.login_fragment), LoginView {
    private val presenter: LoginPresenterImple = LoginPresenterImple(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onActivityCreated(savedInstanceState)
        val prefShar = PreferenceManager.getDefaultSharedPreferences(App.getApplication())
        val lan = prefShar.getString("language", "")
        if (lan!!.isNotEmpty()) {
            when (lan) {
                "uz" -> {
                    bt_uz_login.isSelected = true
                }

                "en" -> {
                    bt_en_login.isSelected = true
                }

                "ru" -> {
                    bt_rus_login.isSelected = true
                }
                else -> {
                    bt_uz_login.isSelected = true
                }
            }
        } else bt_uz_login.isSelected = true
        val editText =
            view!!.findViewById<EditText>(R.id.et_tel_number)
        val listener = MaskedTextChangedListener("[00]-[000]-[00]-[00]", editText)
        editText.addTextChangedListener(listener)
        editText.onFocusChangeListener = listener
        tv_ok.setOnClickListener {
            presenter.login(LoginRequest(et_tel_number.text.toString(), et_pass.text.toString()))
        }

        bt_en_login.setOnClickListener {
            changeLang("en")
            bt_en_login.isSelected = true
            bt_rus_login.isSelected = false
            bt_uz_login.isSelected = false
            prefShar.edit().putString("language", "en").apply()
        }
        bt_rus_login.setOnClickListener {
            changeLang("ru")
            bt_en_login.isSelected = false
            bt_rus_login.isSelected = true
            bt_uz_login.isSelected = false
            prefShar.edit().putString("language", "ru").apply()
        }
        bt_uz_login.setOnClickListener {
            changeLang("uz")
            bt_en_login.isSelected = false
            bt_rus_login.isSelected = false
            bt_uz_login.isSelected = true
            prefShar.edit().putString("language", "uz").apply()
        }
    }

    override fun onLoginSuccess(lohinResponse: LoginResponce) {
        presenter.getVerification()
    }


    override fun onError(error: String) {
        Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
        et_pass.setError("Password is incorret")
//        et_tel_number.setError("Telefon nomer xato")
    }

    override fun onVerificationResponce(verificationResponce: VerificationModel) {

        if (!verificationResponce.verified) {
            val args = Bundle()
            args.putString("INTENT_PHONE", et_tel_number.text.toString())
            val fragment = VerificationFragment()
            fragment.arguments = args
            fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(
                R.id.container2,
                fragment
            )?.commit()
        } else {
            PreferenceManager.getDefaultSharedPreferences(App.getApplication())
                .edit().putString(PREF_USER, et_tel_number.text.toString()).apply()
            presenter.getParentId()
        }

    }

    override fun onDashboardShow() {
        val intent = Intent(context, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun succesSms(): String {
        return et_tel_number.text.toString()
    }

    private fun changeLang(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = activity!!.resources
        val dm = resources.displayMetrics
        val configuration = resources.configuration
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale)
        }else{
            configuration.locale = locale
        }
        resources.updateConfiguration(configuration, dm)
        activity!!.recreate()
    }

}
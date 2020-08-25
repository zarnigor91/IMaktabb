package com.example.imaktab.verification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.imaktab.App
import com.example.imaktab.PREF_USER
import com.example.imaktab.R
import com.example.imaktab.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.sms_verifikation_layout.*
import java.text.SimpleDateFormat


class VerificationFragment : Fragment(R.layout.sms_verifikation_layout), VerificationView {
    var phoneNumber: String? = null
    var cdTimer: CountDownTimer? = null

    private val mPresenter = VerificationPresenterImple(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = this.arguments
        phoneNumber = args!!.getString("INTENT_PHONE")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        restartTimer()
    }

    private fun restartTimer() {
        if(cdTimer!=null){
        cdTimer!!.cancel()
        }
        val format = SimpleDateFormat("mm:ss")
        cdTimer = object : CountDownTimer(120000, 1000) {
            override fun onFinish() {
                btn_ok.isEnabled = false
                tv_timer.text = "00:00"
            }

            override fun onTick(p0: Long) {
                tv_timer.text = format.format(p0).toString()
            }

        }
    }

    private fun initView() {
        tv_tel_number_ver.text = "+998 " + phoneNumber
        et_pin0.requestFocus()
        tv_send_again.setOnClickListener {
            mPresenter.getVerification()
        }
        btn_ok.setOnClickListener {
            onButtonOkClick()
        }
        val imm: InputMethodManager =
            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et_pin0, InputMethodManager.SHOW_IMPLICIT)

        et_pin0.doOnTextChanged { text, start, count, after ->
            if (after==1){
                et_pin1.requestFocus()
            }
        }
        et_pin1.doOnTextChanged { text, start, count, after ->
            if(after==1){
                et_pin2.requestFocus()
            }else{
                et_pin0.requestFocus()
            }
        }
        et_pin2.doOnTextChanged { text, start, count, after ->
            if(after==1){
                et_pin3.requestFocus()
            }else{
                et_pin1.requestFocus()
            }
        }
        et_pin3.doOnTextChanged { text, start, count, after ->
            if(after==1){
                et_pin4.requestFocus()
            }else{
                et_pin2.requestFocus()
            }
        }
        et_pin4.doOnTextChanged { text, start, count, after ->
            if(after==1){
                et_pin5.requestFocus()
            }else{
                et_pin3.requestFocus()
            }
        }
        et_pin5.doOnTextChanged { text, start, count, after ->
            if(after!=1){
                et_pin4.requestFocus()
            }
        }
    }

    private fun onButtonOkClick() {
        val text = et_pin0.text.toString()+ et_pin1.text.toString()+et_pin2.text.toString()+et_pin3.text.toString()+et_pin4.text.toString()+et_pin5.text.toString()
        Log.d("TTT", "kode: " + text)
        mPresenter.checkSms(text)
    }
    private fun onSmsSendAgain() {
        restartTimer()
        et_pin0.setText("")
        et_pin1.setText("")
        et_pin2.setText("")
        et_pin3.setText("")
        et_pin4.setText("")
        et_pin5.setText("")
        btn_ok.isEnabled = true
    }

    override fun succesSms(): String {
        return phoneNumber!!
    }

    override fun onVerificationResponce() {
        onSmsSendAgain()
    }

    override fun onCorrectSms() {
        mPresenter.getParentId()
        val intent = Intent(activity, DashboardActivity::class.java)
        PreferenceManager.getDefaultSharedPreferences(App.getApplication())
            .edit().putString(PREF_USER,     tv_tel_number_ver.text.toString()).apply()
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onIncorrectSms() {
        Toast.makeText(activity, "Code is incorrect", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(cdTimer!=null){
            cdTimer!!.cancel()
        }
        mPresenter.clearDisposable()
    }

}
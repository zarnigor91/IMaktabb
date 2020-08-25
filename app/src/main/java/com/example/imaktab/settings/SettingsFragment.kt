package com.example.imaktab.settings

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.imaktab.App
import com.example.imaktab.BaseFragment
import com.example.imaktab.R
import com.example.imaktab.dashboard.DashboardActivity
import com.example.imaktab.profile.ProfileFragment
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.settings_layout.*
import retrofit2.Response
import java.util.*


class SettingsFragment:BaseFragment(R.layout.settings_layout),SettingsView{
    val presenter:ISettingsPresenter=SettingsPerenterImple(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_save_settings.setOnClickListener {
            if (tv_new_pass.text.toString().equals(tv_con_pass.text.toString()))
            presenter.getNewPass(SettingsRequest(tv_old_pass.text.toString(),tv_new_pass.text.toString()))
            else
            {
                Log.d("sdf","xato")
                Toast.makeText(context,"yangi parol va qayta kiritish paroli bir xil emas",Toast.LENGTH_SHORT).show()
            }
        }

        im_settings_back.setOnClickListener {
            fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(
                R.id.container,
                ProfileFragment()
            )?.commit()
        }

        val prefShar = PreferenceManager.getDefaultSharedPreferences(App.getApplication())
        val lan = prefShar.getString("language", "")
        if (lan!!.isNotEmpty()) {
            when (lan) {
                "uz" -> {
                    bt_uz_set.isSelected = true
                    tv_settings.setText(R.string.settings)
                }

                "en" -> {
                    bt_en_set.isSelected = true
                    tv_settings.setText(R.string.settings)
                }

                "ru" -> {
                    bt_rus_set.isSelected = true
                    tv_settings.setText(R.string.settings)
                }
                else -> {
                    bt_uz_set.isSelected = true
                    tv_settings.setText(R.string.settings)
                }

            }

        }

        bt_en_set.setOnClickListener {
            changeLang("en")
            bt_en_set.isSelected = true
            bt_rus_set.isSelected = false
            bt_uz_set.isSelected = false
            prefShar.edit().putString("language", "en").apply()
//            tv.setText(R.string.hi)
//            tv_settings.setText(R.string.settings)
            tv_pass_change.setText(R.string.change_password)
            tv_push.setText(R.string.Turn_notifications)
            tv_save_settings.setText(R.string.save)
            tv_old_pass.setHint(R.string.old_pass)
            tv_con_pass.setHint(R.string.confirm_pass)

        }
        bt_rus_set.setOnClickListener {
            changeLang("ru")
            bt_en_set.isSelected = false
            bt_rus_set.isSelected = true
            bt_uz_set.isSelected = false
            prefShar.edit().putString("language", "ru").apply()
//            tv_settings.setText(R.string.settings)
            tv_pass_change.setText(R.string.change_password)
            tv_push.setText(R.string.Turn_notifications)
            tv_save_settings.setText(R.string.save)
            tv_old_pass.setHint(R.string.old_pass)
            tv_new_pass.setHint(R.string.new_pass)
            tv_con_pass.setHint(R.string.confirm_pass)
        }
        bt_uz_set.setOnClickListener {
            changeLang("uz")
            bt_en_set.isSelected = false
            bt_rus_set.isSelected = false
            bt_uz_set.isSelected = true
            prefShar.edit().putString("language", "uz").apply()
//            tv_settings.setText(R.string.settings)
            tv_pass_change.setText(R.string.change_password)
            tv_push.setText(R.string.Turn_notifications)
            tv_save_settings.setText(R.string.save)
            tv_old_pass.setHint(R.string.old_pass)
            tv_new_pass.setHint(R.string.new_pass)
            tv_con_pass.setHint(R.string.confirm_pass)
        }
    }

    override fun onSuccess(changePassResponSucces: Response<SettingsResponSucces>) {
       Toast.makeText(context, changePassResponSucces.message()+"parol o`zgardi",Toast.LENGTH_SHORT).show()
    }

    override fun onError() {
        Toast.makeText(context,"xatoeski parol xato",Toast.LENGTH_SHORT).show()
    }

    private fun updateResourcesLegacy(
        context: Context,
        language: String
    ) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(
        context: Context,
        language: String
    ) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        context.createConfigurationContext(configuration)
    }

    private fun changeLang(lang: String) {
        val locale = Locale(lang)
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
        activity?.recreate()
//        (activity as DashboardActivity).finish()

    }


}
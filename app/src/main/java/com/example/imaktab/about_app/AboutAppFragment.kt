package com.example.imaktab.about_app

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.imaktab.BaseFragment
import com.example.imaktab.R
import com.example.imaktab.profile.ProfileFragment
import com.example.imaktab.settings.SettingsFragment
import kotlinx.android.synthetic.main.about_layout.*

class AboutAppFragment:BaseFragment(R.layout.about_layout),AboutAppView{
   private val presenter:IAboutAppPresenter=AboutAppPresenterImple(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressDialog()
        presenter.getAboutApp()
        tv_about_app_name.text=getString(R.string.about_app)
        tv_back_about.setOnClickListener {
            fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.container,
                ProfileFragment()
            )?.commit()
        }
    }

    override fun aboutResponce(aboutModel: AboutModel) {
        hideProgressDialog()
        tv_about_team.text=aboutModel.content.toString()
        tv_about_name.text=aboutModel.name.toString()
    }
}
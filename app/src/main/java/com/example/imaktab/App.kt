package com.example.imaktab

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.imaktab.dashboard.PupilModel
import com.jakewharton.threetenabp.AndroidThreeTen
import java.util.*

public class App :Application(){
    var preferences: SharedPreferences? = null

    companion object{
        var app:Application?=null
        fun getApplication():Application=app!!

        private var apppupilList:List<PupilModel>? = null
        private var token:String? = null
        private var currentPupilId:String? = null

        fun getCurrentPupilId():String? = currentPupilId
        fun setCurrentPupilId(id :String){
            currentPupilId = id
        }

        fun getToken():String? = token
        fun setToken(token:String){
            this.token = token
        }

        fun getPupilList():List<PupilModel>? = apppupilList
        fun setPupilList(pupilList:List<PupilModel>){
            apppupilList=pupilList
        }
    }

    override fun onCreate() {
        super.onCreate()
        app=this
//        val prefShar = PreferenceManager.getDefaultSharedPreferences(this)
//        val lan = prefShar.getString("language", "")
        initLocale("ru")
        AndroidThreeTen.init(this);
    }

    fun getLanguage(){

    }

    private fun initLocale(lan: String) {
        val config = this.resources.configuration
        config.locale = Locale(lan)
        Locale.setDefault(config.locale)
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }



}
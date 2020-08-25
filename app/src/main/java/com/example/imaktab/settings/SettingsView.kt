package com.example.imaktab.settings

import com.example.imaktab.BaseView
import retrofit2.Response

interface SettingsView:BaseView{
   fun onSuccess(changePassResponSucces: Response<SettingsResponSucces>)
    fun onError( )
}
package com.example.imaktab.login

import com.example.imaktab.BaseView
import com.example.imaktab.verification.VerificationModel

interface LoginView :BaseView{
    fun onLoginSuccess(lohinResponse:LoginResponce )
    fun onError(error:String)
    fun onVerificationResponce(verificationModel: VerificationModel)
    fun succesSms():String
    fun onDashboardShow()
}
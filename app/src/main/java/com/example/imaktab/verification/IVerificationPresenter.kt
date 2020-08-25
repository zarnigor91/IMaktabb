package com.example.imaktab.verification

interface IVerificationPresenter{
     fun getVerification()
     fun clearRequest()
    fun checkSms(text: String)
    fun getParentId()
    fun clearDisposable()

}
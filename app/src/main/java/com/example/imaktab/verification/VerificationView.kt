package com.example.imaktab.verification

interface VerificationView{
    fun succesSms(): String
    fun onVerificationResponce()
    fun onCorrectSms()
    fun onIncorrectSms()
}
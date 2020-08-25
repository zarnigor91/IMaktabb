package com.example.imaktab.settings

interface ISettingsPresenter {
    fun getNewPass(changePassRequest: SettingsRequest)
    fun clearRequest()
}
package com.example.imaktab.settings

import com.google.gson.annotations.SerializedName

data class SettingsResponSucces(
    @SerializedName("status")
    val status:String,
    @SerializedName("old_password")
val oldPassword: List<String?>? = listOf()
)
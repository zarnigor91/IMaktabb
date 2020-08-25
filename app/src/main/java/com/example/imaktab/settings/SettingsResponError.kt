package com.example.imaktab.settings

import com.google.gson.annotations.SerializedName

data class SettingsResponError(
    @SerializedName("old_password")
    val oldPassword: List<String?>? = listOf()
)
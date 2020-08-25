package com.example.imaktab.verification


import com.google.gson.annotations.SerializedName

data class CheckSmsResponceModel(
    @SerializedName("verified")
    val verified: Boolean = false
)
package com.example.imaktab.verification


import com.google.gson.annotations.SerializedName

data class VerificationModel(

    @SerializedName("response")
    val response: List<String?>? = listOf(),
    @SerializedName("verified")
    val verified: Boolean = false
)
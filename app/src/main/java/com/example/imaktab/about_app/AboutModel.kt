package com.example.imaktab.about_app


import com.google.gson.annotations.SerializedName

data class AboutModel(
    @SerializedName("content")
    val content: String? = "",
    @SerializedName("name")
    val name: String? = ""
)
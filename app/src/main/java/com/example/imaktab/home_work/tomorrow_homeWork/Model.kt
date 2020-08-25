package com.example.imaktab.home_work.tomorrow_homeWork


import com.google.gson.annotations.SerializedName

data class Model(
    @SerializedName("confirmed")
    val confirmed: Boolean? = false,
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("klass")
    val klass: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("subject")
    val subject: String? = "",
    @SerializedName("task")
    val task: String? = ""
)
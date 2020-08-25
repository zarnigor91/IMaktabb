package com.example.imaktab.home_work.tomorrow_homeWork


import com.google.gson.annotations.SerializedName

data class OtherModel(
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
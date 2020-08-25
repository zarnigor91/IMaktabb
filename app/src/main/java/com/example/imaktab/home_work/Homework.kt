package com.example.imaktab.home_work


import com.google.gson.annotations.SerializedName

data class Homework(
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("klass")
    val klass: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("subject")
    val subject: String? = "",
    @SerializedName("task")
    val task: String? = ""
)
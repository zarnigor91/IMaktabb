package com.example.imaktab.dashboard.continuation

import com.google.gson.annotations.SerializedName

data class SceduleModel(
    @SerializedName("att")
    val att: Boolean? = false,
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("end")
    val end: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("klass")
    val klass: String? = "",
    @SerializedName("mark")
    val mark: String? = "",
    @SerializedName("room")
    val room: String? = "",
    @SerializedName("start")
    val start: String? = "",
    @SerializedName("subject")
    val subject: String? = "",
    @SerializedName("teacher")
    val teacher: String? = ""
)
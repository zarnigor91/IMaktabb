package com.example.imaktab.classScheduleFragment


import com.google.gson.annotations.SerializedName

data class LessonModel(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("teacher")
    val teacher: String? = "",
    @SerializedName("klass")
    val klass: String? = "",
    @SerializedName("subject")
    val subject: String? = "",
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("start")
    val start: String? = "",
    @SerializedName("end")
    val end: String? = "",
    @SerializedName("room")
    val room: String? = ""

)
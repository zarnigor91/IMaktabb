package com.example.imaktab.home_work.passed


import com.google.gson.annotations.SerializedName

data class LessonMarkModel(
    val id: Int,
    val subject: String,
    val confirmed: Boolean,
    val date: String,
    @SerializedName("homework_mark")
    val homeworkMark: String,
    val klass: String,
    val status: String,
    val task: String
)
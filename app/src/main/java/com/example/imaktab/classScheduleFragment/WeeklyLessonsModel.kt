package com.example.imaktab.classScheduleFragment

import com.google.gson.annotations.SerializedName

data class WeeklyLessonsModel(
    @SerializedName("Monday")
    val monday:List<LessonModel>,
    @SerializedName("Tuesday")
    val tuesday:List<LessonModel>,
    @SerializedName("Wednesday")
    val wednesday:List<LessonModel>,
    @SerializedName("Thursday")
    val thursday:List<LessonModel>,
    @SerializedName("Friday")
    val friday:List<LessonModel>,
    @SerializedName("Saturday")
    val saturday:List<LessonModel>
)
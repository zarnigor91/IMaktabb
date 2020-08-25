package com.example.imaktab.home_work.passed

import com.example.imaktab.classScheduleFragment.LessonModel
import com.google.gson.annotations.SerializedName

data class WeekLessonMarkModel(
    @SerializedName("Monday")
    val monday:List<LessonMarkModel>,
    @SerializedName("Tuesday")
    val tuesday:List<LessonMarkModel>,
    @SerializedName("Wednesday")
    val wednesday:List<LessonMarkModel>,
    @SerializedName("Thursday")
    val thursday:List<LessonMarkModel>,
    @SerializedName("Friday")
    val friday:List<LessonMarkModel>,
    @SerializedName("Saturday")
    val saturday:List<LessonMarkModel>
)
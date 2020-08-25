package com.example.imaktab.home_work.passed

data class DailyLessonsMarkModel(
    val dayName:String,
    val date:String,
    var lessons:List<LessonMarkModel>
)
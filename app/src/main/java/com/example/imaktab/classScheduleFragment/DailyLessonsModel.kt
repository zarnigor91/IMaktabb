package com.example.imaktab.classScheduleFragment

import org.threeten.bp.LocalDate

data class DailyLessonsModel(
    val dayName:Int,
    var date:LocalDate,
    var lessons:List<LessonModel>
)
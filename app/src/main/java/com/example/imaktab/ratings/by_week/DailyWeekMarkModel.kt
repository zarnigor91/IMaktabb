package com.example.imaktab.ratings.by_week

import org.threeten.bp.LocalDate

class DailyWeekMarkModel(
    val dayName:Int,
    var date: LocalDate,
    var lessons:List<MarkModel>
)
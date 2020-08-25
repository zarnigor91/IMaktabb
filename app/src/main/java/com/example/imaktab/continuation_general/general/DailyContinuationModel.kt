package com.example.imaktab.continuation_general.general

import org.threeten.bp.LocalDate

data class DailyContinuationModel(
    val dayName:Int,
    var date:LocalDate,
    var lessons:List<ContinuationModel>
)
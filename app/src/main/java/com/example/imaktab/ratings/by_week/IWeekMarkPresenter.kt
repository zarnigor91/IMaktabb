package com.example.imaktab.ratings.by_week

import org.threeten.bp.LocalDate

interface IWeekMarkPresenter{
    fun getWeekMark(date:LocalDate)
    fun clearRequest()
}
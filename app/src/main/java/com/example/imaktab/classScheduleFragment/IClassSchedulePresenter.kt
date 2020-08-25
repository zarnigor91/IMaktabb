package com.example.imaktab.classScheduleFragment

import org.threeten.bp.LocalDate

interface IClassSchedulePresenter {
    fun geteWekSchedule(date: LocalDate)
    fun getPupilListByParentId()
    fun clearRequest()
}
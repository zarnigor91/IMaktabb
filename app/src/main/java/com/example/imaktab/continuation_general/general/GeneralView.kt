package com.example.imaktab.continuation_general.general

import com.example.imaktab.BaseView
import com.example.imaktab.classScheduleFragment.WeeklyLessonsModel

interface GeneralView:BaseView {
    fun getWeekResponce(list: ArrayList<DailyContinuationModel>)
    fun getDate():String
}
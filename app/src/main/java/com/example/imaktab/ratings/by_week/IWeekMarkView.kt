package com.example.imaktab.ratings.by_week

import com.example.imaktab.BaseView
import com.example.imaktab.home_work.passed.WeekMarkModel

interface IWeekMarkView:BaseView{
    fun getWeekvalue(list: ArrayList<DailyWeekMarkModel>)
}
package com.example.imaktab.home_work.passed

import com.example.imaktab.BaseView


interface PassedView:BaseView{
    fun getPassedvalue(weekLessonMarkModel: WeekLessonMarkModel)
    fun onSucces()
    fun onError()
}
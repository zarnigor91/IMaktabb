package com.example.imaktab.classScheduleFragment

import com.example.imaktab.BaseView
import com.example.imaktab.dashboard.PupilModel


interface ClassScheduleView:BaseView {
    fun onSuccess()
    fun onError()
    fun getResponsValue(list:  ArrayList<DailyLessonsModel>)
    fun parentId():Long
    fun onGetPupilSuccess(pupilList:List<PupilModel>)
}
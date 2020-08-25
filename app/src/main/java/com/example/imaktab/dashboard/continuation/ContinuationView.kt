package com.example.imaktab.dashboard.continuation

import com.example.imaktab.BaseView
import com.example.imaktab.dashboard.DashboardResponce
import com.example.imaktab.dashboard.PupilModel
import com.example.imaktab.dashboard.Total

interface ContinuationView:BaseView{
    fun onSuccess()
    fun onError()
    fun getResponsValue(continuationResponce: List<SceduleModel>)
    fun onGetPupilSuccess(pupilList:List<PupilModel>)
    fun parentId():Long
    fun updateTotalGPA(totagpa: Total)
    fun getDashboardValue(dashboardResponce: DashboardResponce)

}
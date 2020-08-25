package com.example.imaktab.home_work

import com.example.imaktab.BaseView
import com.example.imaktab.dashboard.PupilModel
import com.example.imaktab.home_work.tomorrow_homeWork.OtherModel
import com.example.imaktab.home_work.tomorrow_homeWork.TommorowModel

interface HomeWorkView:BaseView {
    fun getTomorrow(tommorowModel: List<TommorowModel>)
    fun getOther(tommorowModel: List<OtherModel>)
    fun parentId():Long
    fun onGetPupilSuccess(pupilList:List<PupilModel>)
}
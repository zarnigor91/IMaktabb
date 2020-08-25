package com.example.imaktab.continuation_general

import com.example.imaktab.dashboard.PupilModel

interface IGeneralConView {
    fun parentId():Long
    fun onGetPupilSuccess(pupilList:List<PupilModel>)
}
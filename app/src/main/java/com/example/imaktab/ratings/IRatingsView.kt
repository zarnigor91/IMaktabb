package com.example.imaktab.ratings

import com.example.imaktab.dashboard.PupilModel

interface IRatingsView {
    fun parentId():Long
    fun onGetPupilSuccess(pupilList:List<PupilModel>)
}
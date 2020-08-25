package com.example.imaktab.continuation_general.sciences

import com.example.imaktab.BaseView

interface SciencesView:BaseView{
//    fun getSciencesResponce(sciencesModel: List<SciencesModel>)
//    fun getSubjectScien(subjects: Subjects)
      fun onGetSubjects(subjects: List<Subjects>)
    fun onGetAttendanceBySubjectAndMonth(attLessonModel: List<SciencesModel>)

}
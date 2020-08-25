package com.example.imaktab.ratings.by_science

import com.example.imaktab.BaseView
import com.example.imaktab.continuation_general.sciences.SciencesModel
import com.example.imaktab.continuation_general.sciences.Subjects

interface IScienMarkView :BaseView{
    fun getSubjectScien(subjects: Subjects)
    fun onGetAttendanceBySubjectAndMonth(markSciensModel: List<MarkSciensModel>)
    fun onGetSubjects(subjects: List<Subjects>)
}
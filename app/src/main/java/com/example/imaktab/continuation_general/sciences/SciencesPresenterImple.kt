package com.example.imaktab.continuation_general.sciences

import android.annotation.SuppressLint
import android.util.Log
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.home_work.confirms.ConfirmRequest
import com.example.imaktab.home_work.confirms.IConfirmsPeresenter
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SciencesPresenterImple(val view: SciencesView):ISciencesPresenter{

    @SuppressLint("CheckResult")
    override fun getAttendanceBySubjectAndMonth(subject: Int, month: Int) {
        var pupilId = App.getCurrentPupilId()
        ApiClient.apiClinet.getContinustionByMonth(pupilId!!.toLong().toInt(), subject,month)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.onGetAttendanceBySubjectAndMonth(it)
                Log.d(IMAKTAB, "SUCCESS on get AttendanceBySubjectAndMonth: $it")
            },{
                Log.e(IMAKTAB, "ERROR on get AttendanceBySubjectAndMonth: "+it.message)
            })
    }

    @SuppressLint("CheckResult")
    override fun getSubjects() {
        ApiClient.apiClinet.getSubjects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.onGetSubjects(it)
                Log.d(IMAKTAB, "SUCCESS on get subjects: $it")
            },{
                Log.e(IMAKTAB, "ERROR on get subjects: " + it.message)
            })
    }

}
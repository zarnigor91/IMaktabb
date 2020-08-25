package com.example.imaktab.ratings.by_science

import android.annotation.SuppressLint
import android.util.Log
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ScienMarkPresenterImple(val view: IScienMarkView):IScienMarkPresenter{

    override fun getMarkBySubjectAndMonth(subject: Int, month: Int) {
        val pupilId = App.getCurrentPupilId()
        view.hideProgressDialog()
        view.showProgressDialog()
        val d= ApiClient.apiClinet.getMarkByMonth(pupilId!!.toLong().toInt(), subject,month)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.hideProgressDialog()
                view.onGetAttendanceBySubjectAndMonth(it)
                Log.d(IMAKTAB, "SUCCESS on get AttendanceBySubjectAndMonth: $it")
            },{
                view.hideProgressDialog()
                Log.e(IMAKTAB, "ERROR on get AttendanceBySubjectAndMonth: "+it.message)
            })
    }
    @SuppressLint("CheckResult")
    override fun getSubjects() {
        view.hideProgressDialog()
        view.showProgressDialog()
        ApiClient.apiClinet.getSubjects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.onGetSubjects(it)
                view.hideProgressDialog()
                Log.d(IMAKTAB, "SUCCESS on get subjects: $it")
            },{
                view.hideProgressDialog()
                Log.e(IMAKTAB, "ERROR on get subjects: " + it.message)
            })
    }

}
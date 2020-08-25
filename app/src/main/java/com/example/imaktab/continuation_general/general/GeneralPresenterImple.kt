package com.example.imaktab.continuation_general.general

import android.util.Log
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.R
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDate

class GeneralPresenterImple(val view: GeneralView):IGeneralPresenter{
    override fun getContinuationWeek( date:LocalDate) {
        val pupilId = App.getCurrentPupilId()
        view.showProgressDialog()
        val data =  "${date.year}-${String.format("%02d", date.month.value)}-${String.format("%02d", date.dayOfMonth)}"
        val data2 =  "${String.format("%02d", date.dayOfMonth)}-${String.format("%02d", date.month.value)}-${date.year}"
        val list = ArrayList<DailyContinuationModel>()
        val d=ApiClient.apiClinet.getWeekContinuation(pupilId!!.toLong(),data2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.monday.isNotEmpty() && it.monday[0].date==data) {
                        list.add(DailyContinuationModel(R.string.monday, date, it.monday))
                        list.add(DailyContinuationModel(R.string.tuesday, date.plusDays(1), it.tuesday))
                        list.add(DailyContinuationModel(R.string.wednesday, date.plusDays(2), it.wednesday))
                        list.add(DailyContinuationModel(R.string.thursday, date.plusDays(3), it.thursday))
                        list.add(DailyContinuationModel(R.string.friday, date.plusDays(4), it.friday))
                        list.add(DailyContinuationModel(R.string.saturday, date.plusDays(5), it.saturday))
                    }
                    else {
                        list.add(DailyContinuationModel(R.string.monday, date, emptyList()))
                        list.add(DailyContinuationModel(R.string.tuesday, date.plusDays(1), emptyList()))
                        list.add(DailyContinuationModel(R.string.wednesday, date.plusDays(2), emptyList()))
                        list.add(DailyContinuationModel(R.string.thursday, date.plusDays(3), emptyList()))
                        list.add(DailyContinuationModel(R.string.friday, date.plusDays(4), emptyList()))
                        list.add(DailyContinuationModel(R.string.saturday, date.plusDays(5), emptyList()))
                    }
                    view.hideProgressDialog()
                    view.getWeekResponce(list)
                },{
                    list.add(DailyContinuationModel(R.string.monday, date, emptyList()))
                    list.add(DailyContinuationModel(R.string.tuesday, date.plusDays(1), emptyList()))
                    list.add(DailyContinuationModel(R.string.wednesday, date.plusDays(2), emptyList()))
                    list.add(DailyContinuationModel(R.string.thursday, date.plusDays(3), emptyList()))
                    list.add(DailyContinuationModel(R.string.friday, date.plusDays(4), emptyList()))
                    list.add(DailyContinuationModel(R.string.saturday, date.plusDays(5), emptyList()))
                    view.hideProgressDialog()
                    view.getWeekResponce(list)
                    Log.e(IMAKTAB, "ERROR week: "+it.message)
                })
    }


}
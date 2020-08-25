package com.example.imaktab.ratings.by_week

import android.util.Log
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.R
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDate

class WeekMarkPresenterImple(val view: IWeekMarkView):IWeekMarkPresenter{
    private var compositeDisposable = CompositeDisposable()
    override fun getWeekMark(date:LocalDate) {
        val pupilId = App.getCurrentPupilId()
        view.showProgressDialog()
        val data =  "${date.year}-${String.format("%02d", date.month.value)}-${String.format("%02d", date.dayOfMonth)}"
        val data2 =  "${String.format("%02d", date.dayOfMonth)}-${String.format("%02d", date.month.value)}-${date.year}"
        val list = ArrayList<DailyWeekMarkModel>()
        val disposable= ApiClient.apiClinet.getMarkByWeek(pupilId!!.toInt(),data2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.monday.isNotEmpty() && it.monday[0].date==data) {
                    list.add(DailyWeekMarkModel(R.string.monday, date, it.monday))
                    list.add(DailyWeekMarkModel(R.string.tuesday, date.plusDays(1), it.tuesday))
                    list.add(DailyWeekMarkModel(R.string.wednesday, date.plusDays(2), it.wednesday))
                    list.add(DailyWeekMarkModel(R.string.thursday, date.plusDays(3), it.thursday))
                    list.add(DailyWeekMarkModel(R.string.friday, date.plusDays(4), it.friday))
                    list.add(DailyWeekMarkModel(R.string.saturday, date.plusDays(5), it.saturday))
                }
                else {
                    list.add(DailyWeekMarkModel(R.string.monday, date, emptyList()))
                    list.add(DailyWeekMarkModel(R.string.tuesday, date.plusDays(1), emptyList()))
                    list.add(DailyWeekMarkModel(R.string.wednesday, date.plusDays(2), emptyList()))
                    list.add(DailyWeekMarkModel(R.string.thursday, date.plusDays(3), emptyList()))
                    list.add(DailyWeekMarkModel(R.string.friday, date.plusDays(4), emptyList()))
                    list.add(DailyWeekMarkModel(R.string.saturday, date.plusDays(5), emptyList()))
                }
                view.hideProgressDialog()
                view.getWeekvalue(list)
            },{
                list.add(DailyWeekMarkModel(R.string.monday, date, emptyList()))
                list.add(DailyWeekMarkModel(R.string.tuesday, date.plusDays(1), emptyList()))
                list.add(DailyWeekMarkModel(R.string.wednesday, date.plusDays(2), emptyList()))
                list.add(DailyWeekMarkModel(R.string.thursday, date.plusDays(3), emptyList()))
                list.add(DailyWeekMarkModel(R.string.friday, date.plusDays(4), emptyList()))
                list.add(DailyWeekMarkModel(R.string.saturday, date.plusDays(5), emptyList()))
                view.hideProgressDialog()
                view.getWeekvalue(list)
                Log.e(IMAKTAB, "ERROR week: "+it.message)
            })
        compositeDisposable.add(disposable)

    }

    override fun clearRequest() {
        compositeDisposable.dispose()
    }

}
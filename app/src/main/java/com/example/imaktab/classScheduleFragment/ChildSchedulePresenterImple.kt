package com.example.imaktab.classScheduleFragment

import android.util.Log
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.R
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

class ChildSchedulePresenterImple(val view: ClassScheduleView):IClassSchedulePresenter{
    private var compositeDisposable = CompositeDisposable()
    override fun geteWekSchedule(date: LocalDate) {
        view.showProgressDialog()
        val data =  "${date.year}-${String.format("%02d", date.month.value)}-${String.format("%02d", date.dayOfMonth)}"
        val data2 =  "${String.format("%02d", date.dayOfMonth)}-${String.format("%02d", date.month.value)}-${date.year}"
        val list = ArrayList<DailyLessonsModel>()
        val pupilId = App.getCurrentPupilId()
        val  disposable = ApiClient.apiClinet.getweekScheduleByPupilId(pupilId!!.toLong(), data2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.monday.isNotEmpty() && it.monday[0].date==data) {
                    list.add(DailyLessonsModel(R.string.monday, date, it.monday))
                    list.add(DailyLessonsModel(R.string.tuesday, date.plusDays(1), it.tuesday))
                    list.add(DailyLessonsModel(R.string.wednesday, date.plusDays(2), it.wednesday))
                    list.add(DailyLessonsModel(R.string.thursday, date.plusDays(3), it.thursday))
                    list.add(DailyLessonsModel(R.string.friday, date.plusDays(4), it.friday))
                    list.add(DailyLessonsModel(R.string.saturday, date.plusDays(5), it.saturday))
                }
                else {
                    list.add(DailyLessonsModel(R.string.monday, date, emptyList()))
                    list.add(DailyLessonsModel(R.string.tuesday, date.plusDays(1), emptyList()))
                    list.add(DailyLessonsModel(R.string.wednesday, date.plusDays(2), emptyList()))
                    list.add(DailyLessonsModel(R.string.thursday, date.plusDays(3), emptyList()))
                    list.add(DailyLessonsModel(R.string.friday, date.plusDays(4), emptyList()))
                    list.add(DailyLessonsModel(R.string.saturday, date.plusDays(5), emptyList()))
                }
                view.hideProgressDialog()
                view.getResponsValue(list)
                Log.e(IMAKTAB, "SUCCESS on get class schedular Today: $it")
            },{
                list.add(DailyLessonsModel(R.string.monday, date, emptyList()))
                list.add(DailyLessonsModel(R.string.tuesday, date.plusDays(1), emptyList()))
                list.add(DailyLessonsModel(R.string.wednesday, date.plusDays(2), emptyList()))
                list.add(DailyLessonsModel(R.string.thursday, date.plusDays(3), emptyList()))
                list.add(DailyLessonsModel(R.string.friday, date.plusDays(4), emptyList()))
                list.add(DailyLessonsModel(R.string.saturday, date.plusDays(5), emptyList()))
                view.hideProgressDialog()
                view.getResponsValue(list)
                Log.e(IMAKTAB, "ERROR on get class schedular Today: "+it.message)
            }
            )
        compositeDisposable.add(disposable)
    }

    override fun getPupilListByParentId() {
        val disposable = ApiClient.apiClinet.getPupilListByParentId(view.parentId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(IMAKTAB, "SUCCESS on get pupilList by parentid: "+it.toString())
                view.onGetPupilSuccess(it)
            },{
                Log.e(IMAKTAB, "ERROR on get pupilList by parentid"+it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun clearRequest() {
        compositeDisposable.dispose()
    }

}
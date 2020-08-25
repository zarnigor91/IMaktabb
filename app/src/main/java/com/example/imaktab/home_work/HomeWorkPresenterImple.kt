package com.example.imaktab.home_work

import android.util.Log
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeWorkPresenterImple(val view: HomeWorkView):
    IHomeWorkPresenter {
    private var compositeDisposable = CompositeDisposable()
    override fun tomorrow() {
        var pupilId = App.getCurrentPupilId()
        val  disposable= ApiClient.apiClinet.getTomorrowByPupilId(pupilId!!.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.getTomorrow(it)
                Log.e(IMAKTAB, "work: "+it.toString())
            },{
                Log.e(IMAKTAB, "ERROR on get class schedular Today: "+it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun other() {
        var pupilId = App.getCurrentPupilId()
        val  disposable= ApiClient.apiClinet.getOtherByPupilId(pupilId!!.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.getOther(it)
                Log.e(IMAKTAB, "work: "+it.toString())
            },{
                Log.e(IMAKTAB, "ERROR on get class schedular Today: "+it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun clearRequest() {
        compositeDisposable.dispose()
    }

    override fun getPupilListByParentId() {
        val disposable = ApiClient.apiClinet.getPupilListByParentId(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(IMAKTAB, "SUCCESS on get pupilList by parentid: "+it.toString())
//                view.onGetPupilSuccess(it)
            },{
                Log.e(IMAKTAB, "ERROR on get pupilList by parentid"+it.message)
            })
        compositeDisposable.add(disposable)
    }

}
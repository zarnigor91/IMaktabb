package com.example.imaktab.home_work.passed

import android.util.Log
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PassedPresenterImple(val view: PassedView):IPassedPresenter{
    private var compositeDisposable = CompositeDisposable()
    override fun getPassed() {
        var pupilId = App.getCurrentPupilId()
         val disposable=ApiClient.apiClinet.getPassedByPupilId(pupilId!!.toLong())
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe({
                view.getPassedvalue(it)
                 Log.e(IMAKTAB, "work: "+it.toString())
                 Log.d("pas","passed"+it)
             },{
                 Log.e(IMAKTAB, "ERROR on get pass: "+it.message)
             })
        compositeDisposable.add(disposable)

    }

    override fun clearRequest() {
        compositeDisposable.dispose()
    }


}

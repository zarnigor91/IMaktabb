package com.example.imaktab.home_work.confirms

import android.util.Log
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ConfirmsPresenterImple( val view:ConfirmsView):IConfirmsPeresenter{
    private var compositeDisposable = CompositeDisposable()
    override fun getConfirms(confirmRequest: ConfirmRequest) {
            var pupilId = App.getCurrentPupilId()
            val  disposable= ApiClient.apiClinet.postConfirms(confirmRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.onSuccess()
                    Log.e(IMAKTAB, "work: "+it.toString())
                },{
                    Log.e(IMAKTAB, "ERROR on get class schedular Today: "+it.message)
                })
            compositeDisposable.add(disposable)
        }

    }

package com.example.imaktab.settings

import android.util.Log
import com.example.imaktab.IMAKTAB
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SettingsPerenterImple(val view:SettingsView):ISettingsPresenter{
    private var compositeDisposable = CompositeDisposable()
    override fun getNewPass(changePassRequest: SettingsRequest) {
        val disposable = ApiClient.apiClinet.changePassword(changePassRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
               if (it.isSuccessful){
                   view.onSuccess(it)
               }
                else{
                   val t=it.body().toString()
                   view.onError()
               }
                Log.e(IMAKTAB, "SUCCESS pass: "+it.toString())
//                view.onGetPupilSuccess(it)
            },{
//                view.onError(it)
                Log.e(IMAKTAB, "pass"+it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun clearRequest() {
        compositeDisposable.dispose()
    }

}
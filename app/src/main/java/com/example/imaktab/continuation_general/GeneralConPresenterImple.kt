package com.example.imaktab.continuation_general

import android.util.Log
import com.example.imaktab.IMAKTAB
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GeneralConPresenterImple(val view: IGeneralConView):IGeneralConPresenter{
    private var compositeDisposable = CompositeDisposable()
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
package com.example.imaktab.verification

import android.util.Log
import androidx.preference.PreferenceManager
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.PARENT_ID_KEY
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VerificationPresenterImple(val view: VerificationView) : IVerificationPresenter {
    private var compositeDisposable = CompositeDisposable()
    override fun getVerification() {
        val disposable = ApiClient.apiClinet.getVerification(view.succesSms())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.onVerificationResponce()
                Log.e(IMAKTAB, "SUCCESS on verification: " + it.toString())
            }, {
                Log.e(IMAKTAB, "ERROR on verification" + it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun clearRequest() {
        compositeDisposable.dispose()
    }

    override fun  checkSms(text: String) {
        val disposable = ApiClient.apiClinet.checkSms(CheckSmsRequestModel(text))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.verified){
                    view.onCorrectSms()
                }else{
                    view.onIncorrectSms()
                }
                Log.e(IMAKTAB, "SUCCESS on verification SMS: " + it.toString())
            }, {
                Log.d("TTT", "error: " + it.message)
                Log.e(IMAKTAB, "ERROR on verification SMS" + it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun getParentId() {
        val disposable = ApiClient.apiClinet.getIdParent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(IMAKTAB, "SUCCESS on get parentId: " + it.id)
                val sharedPreferencesId =
                    PreferenceManager.getDefaultSharedPreferences(App.getApplication())
                var editor = sharedPreferencesId.edit()
                editor.putLong(PARENT_ID_KEY, it.id)
                editor.commit()
            },
                {
                    Log.d(IMAKTAB, "ERROR on get parentId: " + it)
                }
            )

        compositeDisposable.add(disposable)
    }

    override fun clearDisposable() {
        compositeDisposable.clear()
    }
}
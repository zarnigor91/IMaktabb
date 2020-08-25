package com.example.imaktab.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.network.ApiClient
import com.example.imaktab.PARENT_ID_KEY
import com.example.imaktab.TOKEN_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenterImple(val view: LoginView) : ILoginPresenter {
    private var compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun login(loginRequest: LoginRequest) {
        view.showProgressDialog()
        ApiClient.apiClinet.sigIn(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.hideProgressDialog()
                val sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(App.getApplication())
                var editor = sharedPreferences.edit()
                editor.putString(TOKEN_KEY, it.key)
                editor.commit()
                view.onLoginSuccess(it)
                Log.d(IMAKTAB, "SUCCESS on GET TOKEN: Token: ${it.key}")
            }, {
                view.hideProgressDialog()
                view.onError("User not found")
                Log.d(IMAKTAB, " ERROR on get TOKEN" + it.localizedMessage)
            })

    }


    override fun getParentId() {
        ApiClient.apiClinet.getIdParent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(IMAKTAB, "SUCCESS on get parentId: " + it.id)
                val sharedPreferencesId =
                    PreferenceManager.getDefaultSharedPreferences(App.getApplication())
                var editor = sharedPreferencesId.edit()
                editor.putLong(PARENT_ID_KEY, it.id)
                editor.commit()
                view.onDashboardShow()
            },
                {
                    Log.d(IMAKTAB, "ERROR on get parentId: " + it)
                }
            )
    }

    override fun getVerification() {
        val disposable = ApiClient.apiClinet.getVerification(view.succesSms())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(IMAKTAB, "SUCCESS on verification: " + it.toString())
                view.onVerificationResponce(it)
            }, {
                Log.e(IMAKTAB, "ERROR on verification" + it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun clearRequest() {
        compositeDisposable.dispose()
    }
}
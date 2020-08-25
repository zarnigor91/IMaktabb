package com.example.imaktab.about_app
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AboutAppPresenterImple(val view: AboutAppView):IAboutAppPresenter{
    private var compositeDisposable = CompositeDisposable()
    override fun getAboutApp() {
        ApiClient.apiClinet.getAboutApp()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                   view.aboutResponce(it)
            },
                {

                }
            )
    }

    override fun clearRequest() {
        compositeDisposable.dispose()
    }

}
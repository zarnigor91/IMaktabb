package com.example.imaktab.dashboard.continuation

import android.util.Log
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContinuationPresenterImple(val view: ContinuationView):
    IContinuationPresenter {

    override fun getScheduleTodayByPupilId() {
        var pupilId = App.getCurrentPupilId()
        ApiClient.apiClinet.getToday(pupilId!!.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(IMAKTAB, "SUCCESS on get class schedular Today: "+it.toString())
               view.getResponsValue(it)
            },{
                Log.e(IMAKTAB, "ERROR on get class schedular Today: "+it.message)
            })
        view.hideProgressDialog()
    }



    override fun getPupilListByParentId() {
        ApiClient.apiClinet.getPupilListByParentId(view.parentId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(IMAKTAB, "SUCCESS on get pupilList by parentid: "+it.toString())
                view.onGetPupilSuccess(it)
            },{
                Log.e(IMAKTAB, "ERROR on get pupilList by parentid"+it.message)
            })
    }

    override fun getTotal() {
        ApiClient.apiClinet.getTotal()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(IMAKTAB, "SUCCESS on get total_gpa: "+it.toString())
                view.updateTotalGPA(it)
            },{
                Log.e(IMAKTAB, "ERROR on get total gpa: "+it.message)
            })
    }

    override fun getDashboardPupils() {
        var pupilId = App.getCurrentPupilId()
        Log.d("pupilIIId","PUPIULISSS"+pupilId)
        // pupilId null bo'layapti... nega narigilada boru shoshmang
        ApiClient.apiClinet.getDashboardPupil(pupilId!!.toLong())
            .subscribeOn(Schedulers.io())// bo'ldimi ?  ha notogri chaqirilgan ekanmi  boya, ha pupillar kemasdan chqarilgan ekan
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(IMAKTAB, "SUCCESS on get total_gpa: "+it.toString())
                view.getDashboardValue(it) // anaku success kesa oladiyu. shu methodni qayerdandir chaqaisih kk da
            },{
                Log.e(IMAKTAB, "ERROR on get total gpa: "+it.message) // axa xop keyin Nasimxon kechagi ishlamadida picker bitta joyini shundoq qarab berasimi
            })
    }


}
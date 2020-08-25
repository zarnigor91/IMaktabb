package com.example.imaktab.home_work.confirms

import com.example.imaktab.BaseView

interface ConfirmsView:BaseView {
    fun onSuccess()
    fun onError()
}
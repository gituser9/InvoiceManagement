package com.user.invoicemanagement.presenter

import com.user.invoicemanagement.model.Model


abstract class BasePresenter : Presenter {

    lateinit var model: Model


    override fun onStop() {

    }

}
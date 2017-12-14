package com.user.invoicemanagement.presenter

import com.user.invoicemanagement.model.Model
import com.user.invoicemanagement.model.ModelImpl


abstract class BasePresenter : Presenter {

    var model: Model = ModelImpl.instance


    override fun onStop() {

    }

}
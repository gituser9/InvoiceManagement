package com.user.invoicemanagement.presenter

import com.user.invoicemanagement.model.ModelImpl
import com.user.invoicemanagement.view.fragment.ClosedInvoiceFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ClosedInvoicePresenter(val view: ClosedInvoiceFragment) : BasePresenter() {

    init {
        model = ModelImpl()
    }

    fun getInvoice(invoiceId: Long) {
        model.getInvoice(invoiceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { invoice ->
                    view.show(invoice)
                }
    }

}
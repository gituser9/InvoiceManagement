package com.user.invoicemanagement.presenter

import com.user.invoicemanagement.model.ModelImpl
import com.user.invoicemanagement.model.dto.ClosedInvoice
import com.user.invoicemanagement.view.fragment.ArchiveFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ArchivePresenter(private val view: ArchiveFragment) : BasePresenter() {

    init {
        model = ModelImpl()
    }

    fun getAll() {
        model.getAllClosedInvoices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { closedInvoices ->
                    view.showAll(closedInvoices)
                }
    }

    fun deleteInvoice(invoice: ClosedInvoice) {
        model.deleteInvoice(invoice)
        getAll()
    }

}
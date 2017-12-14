package com.user.invoicemanagement.presenter

import android.content.DialogInterface
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.dto.ClosedInvoice
import com.user.invoicemanagement.view.fragment.ArchiveFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ArchivePresenter(private val view: ArchiveFragment) : BasePresenter() {

    fun getAll() {
        model.getAllClosedInvoices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { closedInvoices ->
                    view.showAll(closedInvoices)
                }
    }

    fun deleteInvoice(invoice: ClosedInvoice) {
        view.showConfirm(R.string.delete_closed_invoice_question, DialogInterface.OnClickListener { _: DialogInterface, _: Int ->
            model.deleteInvoice(invoice)
            getAll()
        })
    }

}
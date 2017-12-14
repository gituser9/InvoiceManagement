package com.user.invoicemanagement.presenter

import android.content.DialogInterface
import com.user.invoicemanagement.R
import com.user.invoicemanagement.view.interfaces.MainTabLayoutView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainTabLayoutPresenter(var view: MainTabLayoutView) : BasePresenter() {

    fun getAll() {
        model.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { factoryList ->
                    view.setupTabs(factoryList)
                }
    }

    fun addNewFactory(name: String) {
        val newFactory = model.addNewFactory(name)
        view.addTab(newFactory)
    }

    fun getSummary() {
        model.getSummary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { summary ->
                    view.showSummaryDialog(summary)
                }
    }

    fun closeInvoice() {
        view.showConfirm(R.string.close_invoice_question, DialogInterface.OnClickListener { _: DialogInterface, _: Int ->
            model.closeInvoice()
            view.showToast(R.string.invoice_closed)
            getAll()
        })
    }

    fun deleteFactory(id: Long, tabPosition: Int) {
        view.showConfirm(R.string.delete_factory_question, DialogInterface.OnClickListener { _: DialogInterface, _: Int ->
            model.deleteFactory(id)
            view.removeTab(tabPosition)
        })
    }

    fun filter(name: String) {
        model.filterProducts(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { factoryList ->
                    view.setupTabs(factoryList)
                }
    }

    fun updateFactory(newName: String, factoryId: Long) {
        model.updateFactory(newName, factoryId)
        view.setTabTitle(newName)
    }


}
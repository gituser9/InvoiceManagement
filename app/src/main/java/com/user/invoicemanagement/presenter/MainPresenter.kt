package com.user.invoicemanagement.presenter

import com.user.invoicemanagement.model.ModelImpl
import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.view.fragment.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class MainPresenter(var view: MainView) : BasePresenter() {


    init {
        model = ModelImpl()
    }

    fun getAll() {
        model.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { factoryList ->
                    view.showAll(factoryList)
                }
    }

    fun addNewFactory() {
        model.addNewFactory("New")

        getAll()
    }

    fun addNewProduct(factoryId: Long) {
        model.addNewProduct(factoryId)

        getAll()
    }

    fun deleteProduct(id: Long) {
        model.deleteProduct(id)

        getAll()
    }

    fun updateProduct(product: Product) {
        model.updateProduct(product)
    }

    fun deleteFactory(id: Long) {
        model.deleteFactory(id)

        getAll()
    }

    fun updateFactory(newName: String, factoryId: Long) {
        model.updateFactory(newName, factoryId)

        getAll()
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
        model.closeInvoice()
        getAll()
    }
}
package com.user.invoicemanagement.presenter

import com.user.invoicemanagement.model.ModelImpl
import com.user.invoicemanagement.view.fragment.MainView
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
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



}
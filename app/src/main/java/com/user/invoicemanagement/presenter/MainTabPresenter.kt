package com.user.invoicemanagement.presenter

import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.view.fragment.MainTabFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainTabPresenter(val view: MainTabFragment) : BasePresenter() {

    fun getFactory(id: Long) {
        model.getFactory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { factory ->
                    view.showFactory(factory)
                }
    }

    fun saveAll(products: List<Product>, successMessage: String, showMessage: Boolean) {
        Observable.fromArray(products)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list: List<Product> ->
                    model.saveAll(list)

                    if (showMessage) {
                        view.showToast(successMessage)
                    }
                }
    }

    fun addNewProduct(factoryId: Long) {
        val subscription = model.addNewProduct(factoryId) ?: return
        subscription
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { product ->
                    view.addProduct(product)
                }
    }

    fun deleteProduct(id: Long, position: Int) {
        model.deleteProduct(id)
        view.deleteProductFromView(position)
    }

}
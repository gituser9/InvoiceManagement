package com.user.invoicemanagement.view.fragment



interface BaseView {

    fun filter(name: String)

    fun getAll()

    fun showToast(message: String)

    fun showToast(stringId: Int)

    fun showAlert(stringId: Int)
}
package com.user.invoicemanagement.view.fragment



interface BaseView {

    fun filter(name: String)

    fun getAll()

    fun showToast(message: String)
}
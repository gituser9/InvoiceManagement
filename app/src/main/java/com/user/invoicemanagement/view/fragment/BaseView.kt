package com.user.invoicemanagement.view.fragment

import android.content.DialogInterface


interface BaseView {

    fun filter(name: String)

    fun getAll()

    fun showToast(message: String)

    fun showToast(stringId: Int)

    fun showAlert(stringId: Int)

    fun showConfirm(stringId: Int, listener: DialogInterface.OnClickListener)
}
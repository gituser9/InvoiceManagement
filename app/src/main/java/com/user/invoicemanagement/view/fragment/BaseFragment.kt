package com.user.invoicemanagement.view.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


abstract class BaseFragment : Fragment(), BaseView {

    fun hideKeyboard() {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
    }

    override fun showToast(message: String) {
        Toast.makeText(activity.baseContext, message, Toast.LENGTH_SHORT).show()
    }
}
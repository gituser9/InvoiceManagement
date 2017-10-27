package com.user.invoicemanagement.view.fragment

import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


abstract class BaseFragment : Fragment(), View {

    fun hideKeyboard() {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
    }

    fun showToast(message: String) {
        Toast.makeText(activity.baseContext, message, Toast.LENGTH_SHORT).show()
    }
}
package com.user.invoicemanagement.view.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.user.invoicemanagement.R


abstract class BaseFragment : Fragment(), BaseView {

    fun hideKeyboard() {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
    }

    override fun showToast(message: String) {
        Toast.makeText(activity.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(stringId: Int) {
        Toast.makeText(activity.baseContext, activity.getString(stringId), Toast.LENGTH_SHORT).show()
    }

    override fun showAlert(stringId: Int) {
        AlertDialog.Builder(activity)
                .setIcon(R.drawable.ic_error)
                .setTitle(R.string.error)
                .setMessage(stringId)
                .setNegativeButton(R.string.cancel, { dialog, _ -> dialog.cancel() })
                .create()
                .show()
    }
}
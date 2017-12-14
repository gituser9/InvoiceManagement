package com.user.invoicemanagement.view.fragment.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.other.Constant
import kotlinx.android.synthetic.main.dialog_summary.view.*


class SummaryDialogFragment : DialogFragment() {

    var summary: Summary? = null   // TODO: from Bundle (now bundle is null)

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_summary, null)

        val x = Constant.priceFormat.format(summary?.sellingSummary)

        if (summary != null) {
            view.purchaseSummary.text = Constant.priceFormat.format(summary?.purchaseSummary)
            view.sellingSummary.text = Constant.priceFormat.format(summary?.sellingSummary)
        }

        builder.setView(view)
                .setCancelable(true)
                .setPositiveButton(R.string.ok, { dialog, _ -> dialog.cancel() })

        return builder.create()
    }

}
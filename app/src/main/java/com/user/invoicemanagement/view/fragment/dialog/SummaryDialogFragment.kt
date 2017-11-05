package com.user.invoicemanagement.view.fragment.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.data.Summary
import kotlinx.android.synthetic.main.dialog_summary.view.*
import java.text.DecimalFormat
import java.text.NumberFormat


class SummaryDialogFragment : DialogFragment() {

    lateinit var summary: Summary   // TODO: from Bundle (now bundle is null)

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_summary, null)

        /*if (savedInstanceState != null) {
            view.purchaseSummary.text = arguments.getString("purchaseSummary")
            view.sellingSummary.text = arguments.getString("sellingSummary")
        }*/

        val baseFormat = NumberFormat.getCurrencyInstance()
        val decimalFormatSymbols = (baseFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        (baseFormat as DecimalFormat).decimalFormatSymbols = decimalFormatSymbols

        view.purchaseSummary.text = baseFormat.format(summary.purchaseSummary)
        view.sellingSummary.text = baseFormat.format(summary.sellingSummary)

        builder.setView(view)
                .setCancelable(true)
                .setPositiveButton(R.string.ok, { dialog, _ -> dialog.cancel() })

        return builder.create()
    }

}
package com.user.invoicemanagement.view.fragment.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.user.invoicemanagement.R
import kotlinx.android.synthetic.main.dialog_set_weight.view.*


class SetWeightDialogFragment : DialogFragment() {

    lateinit var minusButtonListener: View.OnClickListener
    lateinit var resetButtonListener: View.OnClickListener
    lateinit var plusButtonListener: View.OnClickListener
    lateinit var edtNewWeight: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_set_weight, null)

        view.findViewById<Button>(R.id.btnWeightMinus).setOnClickListener(minusButtonListener)
        view.findViewById<Button>(R.id.btnWeightReset).setOnClickListener(resetButtonListener)
        view.findViewById<Button>(R.id.btnWeightPlus).setOnClickListener(plusButtonListener)
        edtNewWeight = view.findViewById(R.id.edtNewWeight)

        return builder.setView(view).setCancelable(true).create()
    }

}
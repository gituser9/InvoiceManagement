package com.user.invoicemanagement.view.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import com.user.invoicemanagement.R
import com.user.invoicemanagement.other.Constant


class SetWeightDialogFragment() : DialogFragment() {

    lateinit var edtNewWeight: EditText
    lateinit var button: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_set_weight, null)

        edtNewWeight = view.findViewById(R.id.edtNewWeight)

        view.findViewById<Button>(R.id.btnWeightMinus).setOnClickListener {
            dismiss()
            val oldValue = button.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
            val value = edtNewWeight.text.toString().toFloatOrNull() ?: 0f
            button.setText(Constant.priceFormat.format(oldValue - value))
        }
        view.findViewById<Button>(R.id.btnWeightReset).setOnClickListener {
            dismiss()
            button.setText("0.0")
        }
        view.findViewById<Button>(R.id.btnWeightPlus).setOnClickListener {
            val oldValue = button.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
            val value = edtNewWeight.text.toString().replace(',', '.').toFloatOrNull() ?: 0f

            button.setText(Constant.priceFormat.format(oldValue + value).replace(',', '.'))
            dismiss()
        }

        return builder.setView(view).setCancelable(true).create()
    }
}
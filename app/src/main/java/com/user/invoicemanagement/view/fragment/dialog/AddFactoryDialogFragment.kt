package com.user.invoicemanagement.view.fragment.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.EditText
import com.user.invoicemanagement.R


class AddFactoryDialogFragment : DialogFragment() {

    lateinit var positiveListener: DialogInterface.OnClickListener
    lateinit var edtFactoryName: EditText


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_factory, null)
        edtFactoryName = view.findViewById(R.id.edtNewFactoryName)

        builder.setView(view)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, { dialog, _ -> dialog.cancel() })
                .setPositiveButton(R.string.ok, positiveListener)

        return builder.create()
    }

}
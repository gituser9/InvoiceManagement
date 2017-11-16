package com.user.invoicemanagement.view.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.data.WeightEnum
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.other.Constant


class SetWeightDialogFragment : DialogFragment() {

    lateinit var edtNewWeight: EditText
    lateinit var button: Button
    lateinit var product: Product
    lateinit var weightEnum: WeightEnum


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_set_weight, null)

        edtNewWeight = view.findViewById(R.id.edtNewWeight)

        view.findViewById<Button>(R.id.btnWeightMinus).setOnClickListener {
            dismiss()
            val oldValue = button.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
            val value = edtNewWeight.text.toString().toFloatOrNull() ?: 0f

            setProductWeight(product, oldValue - value)
            button.text = Constant.baseFormat.format(oldValue - value)
        }
        view.findViewById<Button>(R.id.btnWeightReset).setOnClickListener {
            dismiss()
            setProductWeight(product, 0f)
            button.text = "0.0"
        }
        view.findViewById<Button>(R.id.btnWeightPlus).setOnClickListener {
            val oldValue = button.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
            val value = edtNewWeight.text.toString().replace(',', '.').toFloatOrNull() ?: 0f

            setProductWeight(product, oldValue + value)
            button.text = Constant.baseFormat.format(oldValue + value).replace(',', '.')
            dismiss()
        }

        return builder.setView(view).setCancelable(true).create()
    }

    private fun setProductWeight(product: Product, weightValue: Float) {
        when(weightEnum) {
            WeightEnum.WEIGHT_1 -> { product.weightOnStore = weightValue }
            WeightEnum.WEIGHT_2 -> { product.weightInFridge = weightValue }
            WeightEnum.WEIGHT_3 -> { product.weightInStorage = weightValue }
            WeightEnum.WEIGHT_4 -> { product.weight4 = weightValue }
            WeightEnum.WEIGHT_5 -> { product.weight5 = weightValue }
        }
    }
}
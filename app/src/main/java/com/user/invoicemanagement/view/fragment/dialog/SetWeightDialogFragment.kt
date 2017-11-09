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


class SetWeightDialogFragment() : DialogFragment() {

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
            when(weightEnum) {
                WeightEnum.WEIGHT_1 -> { product.weightOnStore = oldValue - value }
                WeightEnum.WEIGHT_2 -> { product.weightInFridge = oldValue - value }
                WeightEnum.WEIGHT_3 -> { product.weightInStorage = oldValue - value }
                WeightEnum.WEIGHT_4 -> { product.weight4 = oldValue - value }
                WeightEnum.WEIGHT_5 -> { product.weight5 = oldValue - value }
            }
            button.text = Constant.priceFormat.format(oldValue - value)
        }
        view.findViewById<Button>(R.id.btnWeightReset).setOnClickListener {
            dismiss()
            when(weightEnum) {
                WeightEnum.WEIGHT_1 -> { product.weightOnStore = 0f }
                WeightEnum.WEIGHT_2 -> { product.weightInFridge = 0f }
                WeightEnum.WEIGHT_3 -> { product.weightInStorage = 0f }
                WeightEnum.WEIGHT_4 -> { product.weight4 = 0f }
                WeightEnum.WEIGHT_5 -> { product.weight5 = 0f }
            }
            button.text = "0.0"
        }
        view.findViewById<Button>(R.id.btnWeightPlus).setOnClickListener {
            val oldValue = button.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
            val value = edtNewWeight.text.toString().replace(',', '.').toFloatOrNull() ?: 0f

            when(weightEnum) {
                WeightEnum.WEIGHT_1 -> { product.weightOnStore = oldValue + value }
                WeightEnum.WEIGHT_2 -> { product.weightInFridge = oldValue + value }
                WeightEnum.WEIGHT_3 -> { product.weightInStorage = oldValue + value }
                WeightEnum.WEIGHT_4 -> { product.weight4 = oldValue + value }
                WeightEnum.WEIGHT_5 -> { product.weight5 = oldValue + value }
            }

//            editText.setText(Constant.priceFormat.format(oldValue + value).replace(',', '.'))
            button.text = Constant.priceFormat.format(oldValue + value).replace(',', '.')
            dismiss()
        }

        return builder.setView(view).setCancelable(true).create()
    }
}
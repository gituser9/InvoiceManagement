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
import com.user.invoicemanagement.view.interfaces.Saver


class SetWeightDialogFragment : DialogFragment() {

    lateinit var edtNewWeight: EditText
    lateinit var button: Button
    lateinit var product: Product
    lateinit var weightEnum: WeightEnum
    var fragment: Saver? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_set_weight, null)

        edtNewWeight = view.findViewById(R.id.edtNewWeight)

        view.findViewById<Button>(R.id.btnWeightMinus).setOnClickListener {
            dismiss()
            val oldValue = prepareString(button.text.toString()).toFloatOrNull() ?: 0f
            val value = prepareString(edtNewWeight.text.toString()).toFloatOrNull() ?: 0f

            setProductWeight(product, oldValue - value)
            button.text = Constant.priceFormat.format(oldValue - value)
            fragment?.saveAll()
        }
        view.findViewById<Button>(R.id.btnWeightReset).setOnClickListener {
            dismiss()
            setProductWeight(product, 0f)
            button.text = "0.0"
            fragment?.saveAll()
        }
        view.findViewById<Button>(R.id.btnWeightPlus).setOnClickListener {
            val oldValue = prepareString(button.text.toString()).toFloatOrNull() ?: 0f
            val value = prepareString(edtNewWeight.text.toString()).toFloatOrNull() ?: 0f

            setProductWeight(product, oldValue + value)
            button.text = Constant.priceFormat.format(oldValue + value).replace(',', '.')
            fragment?.saveAll()
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

    private fun prepareString(string: String): String {
        return string.trim()
                .replace(',', '.')
                .replace(Constant.whiteSpaceRegex, "")
    }
}
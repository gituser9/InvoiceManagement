package com.user.invoicemanagement.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_main_item.view.*


class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val edtName: EditText = itemView.edtName

    val btnWeightOnStore: Button = itemView.btnWeightOnStore
    val btnWeightInFridge: Button = itemView.btnWeightOnFridge
    val btnWeightInStorage: Button = itemView.btnWeightOnStorage
    val btnWeight4: Button = itemView.btnWeight4
    val btnWeight5: Button = itemView.btnWeight5

    val edtPurchasePrice: EditText = itemView.edtPurchasePrice
    val edtSellingPrice: EditText = itemView.edtSellingPrice

    val tvPurchasePriceSummary: TextView = itemView.tvPurchasePriceSummary
    val tvSellingPriceSummary: TextView = itemView.tvSellingPriceSummary

    val btnDeleteProduct: ImageButton = itemView.btnDeleteProduct

    fun purchasePriceSummary(): Float {
        val edtWeightOnStoreFloat =  btnWeightOnStore.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        val edtWeightInFridgeFloat =  btnWeightInFridge.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        val edtWeightInStorageFloat =  btnWeightInStorage.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        val edtWeight4Float =  btnWeight4.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        val edtWeight5Float =  btnWeight5.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        val edtPurchasePriceFloat =  edtPurchasePrice.text.toString().replace(',', '.').toFloatOrNull() ?: 0f

        return (edtWeightOnStoreFloat + edtWeightInFridgeFloat + edtWeightInStorageFloat + edtWeight4Float + edtWeight5Float) * edtPurchasePriceFloat
    }

    fun sellingPriceSummary(): Float {
        val edtWeightOnStoreFloat =  btnWeightOnStore.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        val edtWeightInFridgeFloat =  btnWeightInFridge.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        val edtWeightInStorageFloat =  btnWeightInStorage.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        val edtWeight4Float =  btnWeight4.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        val edtWeight5Float =  btnWeight5.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        val edtSellingPriceFloat =  edtSellingPrice.text.toString().replace(',', '.').toFloatOrNull() ?: 0f

        return (edtWeightOnStoreFloat + edtWeightInFridgeFloat + edtWeightInStorageFloat + edtWeight4Float + edtWeight5Float) * edtSellingPriceFloat
    }

}

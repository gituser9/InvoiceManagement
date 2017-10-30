package com.user.invoicemanagement.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_main_item.view.*


class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val edtName: EditText = itemView.edtName

    val edtWeightOnStore: EditText = itemView.edtWeightOnStore
    val edtWeightInFridge: EditText = itemView.edtWeightOnFridge
    val edtWeightInStorage: EditText = itemView.edtWeightOnStorage
    val edtWeight4: EditText = itemView.edtWeight4
    val edtWeight5: EditText = itemView.edtWeight5

    val edtPurchasePrice: EditText = itemView.edtPurchasePrice
    val edtSellingPrice: EditText = itemView.edtSellingPrice

    val tvPurchasePriceSummary: TextView = itemView.tvPurchasePriceSummary
    val tvSellingPriceSummary: TextView = itemView.tvSellingPriceSummary

    val btnDeleteProduct: ImageButton = itemView.btnDeleteProduct

    fun purchasePriceSummary(): Float {
        val edtWeightOnStoreFloat =  edtWeightOnStore.text.toString().toFloat()
        val edtWeightInFridgeFloat =  edtWeightInFridge.text.toString().toFloat()
        val edtWeightInStorageFloat =  edtWeightInStorage.text.toString().toFloat()
        val edtWeight4Float =  edtWeight4.text.toString().toFloat()
        val edtWeight5Float =  edtWeight5.text.toString().toFloat()
        val edtPurchasePriceFloat =  edtPurchasePrice.text.toString().toFloat()

        return (edtWeightOnStoreFloat + edtWeightInFridgeFloat + edtWeightInStorageFloat + edtWeight4Float + edtWeight5Float) * edtPurchasePriceFloat
    }

    fun sellingPriceSummary(): Float {
        val edtWeightOnStoreFloat =  edtWeightOnStore.text.toString().toFloat()
        val edtWeightInFridgeFloat =  edtWeightInFridge.text.toString().toFloat()
        val edtWeightInStorageFloat =  edtWeightInStorage.text.toString().toFloat()
        val edtWeight4Float =  edtWeight4.text.toString().toFloat()
        val edtWeight5Float =  edtWeight5.text.toString().toFloat()
        val edtSellingPriceFloat =  edtSellingPrice.text.toString().toFloat()

        return (edtWeightOnStoreFloat + edtWeightInFridgeFloat + edtWeightInStorageFloat + edtWeight4Float + edtWeight5Float) * edtSellingPriceFloat
    }

}

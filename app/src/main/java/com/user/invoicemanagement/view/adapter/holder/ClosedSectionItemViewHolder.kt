package com.user.invoicemanagement.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_closed_invoice_item.view.*


class ClosedSectionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val edtName: TextView = itemView.edtName

    val edtWeightOnStore: TextView = itemView.edtWeightOnStore
    val edtWeightInFridge: TextView = itemView.edtWeightOnFridge
    val edtWeightInStorage: TextView = itemView.edtWeightOnStorage
    val edtWeight4: TextView = itemView.edtWeight4
    val edtWeight5: TextView = itemView.edtWeight5

    val edtPurchasePrice: TextView = itemView.edtPurchasePrice
    val edtSellingPrice: TextView = itemView.edtSellingPrice

    val tvPurchasePriceSummary: TextView = itemView.tvPurchasePriceSummary
    val tvSellingPriceSummary: TextView = itemView.tvSellingPriceSummary

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

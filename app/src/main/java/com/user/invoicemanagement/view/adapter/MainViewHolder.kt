package com.user.invoicemanagement.view.adapter;

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.user.invoicemanagement.other.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_main_item.view.*
import java.util.concurrent.TimeUnit


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

    init {
        RxTextView.afterTextChangeEvents(edtWeightOnStore)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    tvPurchasePriceSummary.text = Constant.priceFormat.format(purchasePriceSummary())
                    tvSellingPriceSummary.text = Constant.priceFormat.format(sellingPriceSummary())
                }
        RxTextView.afterTextChangeEvents(edtWeightInFridge)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    tvPurchasePriceSummary.text = Constant.priceFormat.format(purchasePriceSummary())
                    tvSellingPriceSummary.text = Constant.priceFormat.format(sellingPriceSummary())
                }
        RxTextView.afterTextChangeEvents(edtWeightInStorage)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    tvPurchasePriceSummary.text = Constant.priceFormat.format(purchasePriceSummary())
                    tvSellingPriceSummary.text = Constant.priceFormat.format(sellingPriceSummary())
                }
        RxTextView.afterTextChangeEvents(edtWeight4)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    tvPurchasePriceSummary.text = Constant.priceFormat.format(purchasePriceSummary())
                    tvSellingPriceSummary.text = Constant.priceFormat.format(sellingPriceSummary())
                }
        RxTextView.afterTextChangeEvents(edtWeight5)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    tvPurchasePriceSummary.text = Constant.priceFormat.format(purchasePriceSummary())
                    tvSellingPriceSummary.text = Constant.priceFormat.format(sellingPriceSummary())
                }
        RxTextView.afterTextChangeEvents(edtPurchasePrice)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    tvPurchasePriceSummary.text = Constant.priceFormat.format(purchasePriceSummary())
                    tvSellingPriceSummary.text = Constant.priceFormat.format(sellingPriceSummary())
                }
        RxTextView.afterTextChangeEvents(edtSellingPrice)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    tvPurchasePriceSummary.text = Constant.priceFormat.format(purchasePriceSummary())
                    tvSellingPriceSummary.text = Constant.priceFormat.format(sellingPriceSummary())
                }
    }
}

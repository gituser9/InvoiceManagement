package com.user.invoicemanagement.view.fragment

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.other.Constant
import com.user.invoicemanagement.view.adapter.MainHeaderViewHolder
import com.user.invoicemanagement.view.adapter.MainViewHolder
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.NumberFormat
import java.util.concurrent.TimeUnit


class Section(sectionParameters: SectionParameters, private val factory: ProductFactory, private var list: List<Product>, private val mainView: MainView) : StatelessSection(sectionParameters) {

    override fun getContentItemsTotal(): Int = list.size

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder = MainViewHolder(view)

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val itemHolder = holder as MainViewHolder
        val product = list[position]

        itemHolder.edtName.setText(product.name)
        itemHolder.edtWeightOnStore.setText(product.weightOnStore.toString())
        itemHolder.edtWeightInFridge.setText(product.weightInFridge.toString())
        itemHolder.edtWeightInStorage.setText(product.weightInStorage.toString())
        itemHolder.edtWeight4.setText(product.weight4.toString())
        itemHolder.edtWeight5.setText(product.weight5.toString())

        itemHolder.edtPurchasePrice.setText(product.purchasePrice.toString())
        itemHolder.edtSellingPrice.setText(product.sellingPrice.toString())

        itemHolder.tvPurchasePriceSummary.text = Constant.priceFormat.format(product.purchasePriceSummary)
        itemHolder.tvSellingPriceSummary.text = Constant.priceFormat.format(product.sellingPriceSummary)

        itemHolder.btnDeleteProduct.setOnClickListener {
            mainView.deleteProduct(product.id)
        }

    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder = MainHeaderViewHolder(view)

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val itemHolder = holder as MainHeaderViewHolder
        itemHolder.tvHeader.text = factory.name


        itemHolder.btnAddProduct.setOnClickListener {
            mainView.addNewProduct(factory.id)
        }
    }
}
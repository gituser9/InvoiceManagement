package com.user.invoicemanagement.view.fragment

import android.support.v7.widget.RecyclerView
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.other.Constant
import com.user.invoicemanagement.view.adapter.holder.MainFooterViewHolder
import com.user.invoicemanagement.view.adapter.holder.MainHeaderViewHolder
import com.user.invoicemanagement.view.adapter.holder.MainViewHolder
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit


class Section(sectionParameters: SectionParameters, private val factory: ProductFactory, private var list: List<Product>, private val mainView: MainView) : StatelessSection(sectionParameters) {

    private var footerHolder: MainFooterViewHolder? = null
    private val baseFormat = NumberFormat.getCurrencyInstance(Locale("ru"))


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
        RxTextView.afterTextChangeEvents(itemHolder.edtName)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    mainView.updateProduct(getProductViewData(itemHolder, product.id))
                }
        RxTextView.afterTextChangeEvents(itemHolder.edtWeightOnStore)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> updateSummaryData(itemHolder, product) }
        RxTextView.afterTextChangeEvents(itemHolder.edtWeightInFridge)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> updateSummaryData(itemHolder, product) }
        RxTextView.afterTextChangeEvents(itemHolder.edtWeightInStorage)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> updateSummaryData(itemHolder, product) }
        RxTextView.afterTextChangeEvents(itemHolder.edtWeight4)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> updateSummaryData(itemHolder, product) }
        RxTextView.afterTextChangeEvents(itemHolder.edtWeight5)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> updateSummaryData(itemHolder, product) }
        RxTextView.afterTextChangeEvents(itemHolder.edtPurchasePrice)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> updateSummaryData(itemHolder, product) }
        RxTextView.afterTextChangeEvents(itemHolder.edtSellingPrice)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> updateSummaryData(itemHolder, product) }

    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder = MainHeaderViewHolder(view)

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val itemHolder = holder as MainHeaderViewHolder
        itemHolder.tvHeader.text = factory.name

        itemHolder.btnAddProduct.setOnClickListener {
            mainView.addNewProduct(factory.id)
        }
        itemHolder.btnEditName.setOnClickListener {
            mainView.showEditFactoryDialog(factory)
        }
        itemHolder.btnDelete.setOnClickListener {
            mainView.deleteFactory(factory.id)
        }
    }

    override fun getFooterViewHolder(view: View): RecyclerView.ViewHolder = MainFooterViewHolder(view)

    override fun onBindFooterViewHolder(holder: RecyclerView.ViewHolder?) {
        val itemHolder = holder as MainFooterViewHolder
        var purchaseSummary = 0f
        var sellingSummary = 0f
        footerHolder = itemHolder

        list.forEach { product: Product ->
            purchaseSummary += product.purchasePriceSummary
            sellingSummary += product.sellingPriceSummary
        }

        itemHolder.mainFooterPurchaseSummary.text = purchaseSummary.toString()
        itemHolder.mainFooterSellingSummary.text = sellingSummary.toString()
    }

    private fun updateSummaryData(itemHolder: MainViewHolder, product: Product) {
        updateProductView(itemHolder, product)
        mainView.updateProduct(product)
        itemHolder.tvPurchasePriceSummary.text = baseFormat.format(itemHolder.purchasePriceSummary())
        itemHolder.tvSellingPriceSummary.text = baseFormat.format(itemHolder.sellingPriceSummary())
        setFooterData()
    }

    private fun setFooterData() {
        if (footerHolder == null) {
            return
        }
        var purchaseSummary = 0f
        var sellingSummary = 0f

        list.forEach { product: Product ->
            purchaseSummary += product.purchasePriceSummary
            sellingSummary += product.sellingPriceSummary
        }

        footerHolder?.mainFooterPurchaseSummary?.text = purchaseSummary.toString()
        footerHolder?.mainFooterSellingSummary?.text = sellingSummary.toString()
    }

    private fun updateProductView(itemHolder: MainViewHolder, product: Product) {
        val newProduct = getProductViewData(itemHolder, product.id)
        product.name = newProduct.name
        product.weightOnStore = newProduct.weightOnStore
        product.weightInFridge = newProduct.weightInFridge
        product.weightInStorage = newProduct.weightInStorage
        product.weight4 = newProduct.weight4
        product.weight5 = newProduct.weight5
        product.purchasePrice = newProduct.purchasePrice
        product.sellingPrice = newProduct.sellingPrice
    }

    private fun getProductViewData(holder: MainViewHolder, id: Long): Product {
        val product = Product()
        product.id = id
        product.name = holder.edtName.text.toString()
        product.weightOnStore = holder.edtWeightOnStore.text.toString().toFloat()
        product.weightInFridge = holder.edtWeightInFridge.text.toString().toFloat()
        product.weightInStorage = holder.edtWeightInStorage.text.toString().toFloat()
        product.weight4 = holder.edtWeight4.text.toString().toFloat()
        product.weight5 = holder.edtWeight5.text.toString().toFloat()
        product.purchasePrice = holder.edtPurchasePrice.text.toString().toFloat()
        product.sellingPrice = holder.edtSellingPrice.text.toString().toFloat()

        return product
    }
}
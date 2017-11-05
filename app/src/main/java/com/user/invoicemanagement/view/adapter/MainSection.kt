package com.user.invoicemanagement.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.other.Constant
import com.user.invoicemanagement.view.adapter.holder.MainFooterViewHolder
import com.user.invoicemanagement.view.adapter.holder.MainHeaderViewHolder
import com.user.invoicemanagement.view.adapter.holder.MainViewHolder
import com.user.invoicemanagement.view.fragment.MainView
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainSection(sectionParameters: SectionParameters, private val factory: ProductFactory, private var list: List<Product>, private val mainView: MainView) : StatelessSection(sectionParameters) {

    private var footerHolder: MainFooterViewHolder? = null
    private val baseFormat = NumberFormat.getCurrencyInstance(Locale("ru"))


    override fun getContentItemsTotal(): Int = list.size

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder = MainViewHolder(view)

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val itemHolder = holder as MainViewHolder
        val product = list[position]

        itemHolder.edtName.setText(product.name)
        itemHolder.btnWeightOnStore.setText(product.weightOnStore.toString())
        itemHolder.btnWeightInFridge.setText(product.weightInFridge.toString())
        itemHolder.btnWeightInStorage.setText(product.weightInStorage.toString())
        itemHolder.btnWeight4.setText(product.weight4.toString())
        itemHolder.btnWeight5.setText(product.weight5.toString())

        if (product.purchasePrice != 0f) {
            itemHolder.edtPurchasePrice.setText(product.purchasePrice.toString())
        }
        if (product.sellingPrice != 0f) {
            itemHolder.edtSellingPrice.setText(product.sellingPrice.toString())
        }

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
        itemHolder.btnWeightOnStore.setOnClickListener { mainView.showSetWeightDialog(itemHolder.btnWeightOnStore) }
        itemHolder.btnWeightInFridge.setOnClickListener { mainView.showSetWeightDialog(itemHolder.btnWeightInFridge) }
        itemHolder.btnWeightInStorage.setOnClickListener { mainView.showSetWeightDialog(itemHolder.btnWeightInStorage) }
        itemHolder.btnWeight4.setOnClickListener { mainView.showSetWeightDialog(itemHolder.btnWeight4) }
        itemHolder.btnWeight5.setOnClickListener { mainView.showSetWeightDialog(itemHolder.btnWeight5) }

        RxTextView.afterTextChangeEvents(itemHolder.btnWeightOnStore)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> updateSummaryData(itemHolder, product) }
        RxTextView.afterTextChangeEvents(itemHolder.btnWeightInFridge)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> updateSummaryData(itemHolder, product) }
        RxTextView.afterTextChangeEvents(itemHolder.btnWeightInStorage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> updateSummaryData(itemHolder, product) }
        RxTextView.afterTextChangeEvents(itemHolder.btnWeight4)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> updateSummaryData(itemHolder, product) }
        RxTextView.afterTextChangeEvents(itemHolder.btnWeight5)
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
        product.weightOnStore = holder.btnWeightOnStore.text.toString().toFloatOrNull() ?: 0f
        product.weightInFridge = holder.btnWeightInFridge.text.toString().toFloatOrNull() ?: 0f
        product.weightInStorage = holder.btnWeightInStorage.text.toString().toFloatOrNull() ?: 0f
        product.weight4 = holder.btnWeight4.text.toString().toFloatOrNull() ?: 0f
        product.weight5 = holder.btnWeight5.text.toString().toFloatOrNull() ?: 0f
        product.purchasePrice = holder.edtPurchasePrice.text.toString().toFloatOrNull() ?: 0f
        product.sellingPrice = holder.edtSellingPrice.text.toString().toFloatOrNull() ?: 0f

        return product
    }
}
package com.user.invoicemanagement.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.user.invoicemanagement.model.data.WeightEnum
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.other.Constant
import com.user.invoicemanagement.view.adapter.holder.MainFooterViewHolder
import com.user.invoicemanagement.view.adapter.holder.MainHeaderViewHolder
import com.user.invoicemanagement.view.adapter.holder.MainViewHolder
import com.user.invoicemanagement.view.interfaces.MainView
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection


class MainSection(sectionParameters: SectionParameters, private val factory: ProductFactory, var list: List<Product>, private val mainView: MainView) : StatelessSection(sectionParameters) {

    private var footerHolder: MainFooterViewHolder? = null
    private var holders = mutableListOf<MainViewHolder>()


    override fun getContentItemsTotal(): Int = list.size

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder = MainViewHolder(view)

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val itemHolder = holder as MainViewHolder
        val product = list[position]

        holders.add(itemHolder)
        itemHolder.product = product
        itemHolder.setListener(View.OnLongClickListener {
            if (itemHolder.product != null) {
                mainView.deleteProduct(itemHolder.product!!)
            }
            true
        })
        itemHolder.edtName.setText(product.name)
        itemHolder.btnWeightOnStore.text = product.weightOnStore.toString()
        itemHolder.btnWeightInFridge.text = product.weightInFridge.toString()
        itemHolder.btnWeightInStorage.text = product.weightInStorage.toString()
        itemHolder.btnWeight4.text = product.weight4.toString()
        itemHolder.btnWeight5.text = product.weight5.toString()

        itemHolder.edtWeightOnStore.setText(product.weightOnStore.toString())
        itemHolder.edtWeightInFridge.setText(product.weightInFridge.toString())
        itemHolder.edtWeightInStorage.setText(product.weightInStorage.toString())
        itemHolder.edtWeight4.setText(product.weight4.toString())
        itemHolder.edtWeight5.setText(product.weight5.toString())

        if (product.purchasePrice != 0f) {
            itemHolder.edtPurchasePrice.setText(product.purchasePrice.toString())
        }
        if (product.sellingPrice != 0f) {
            itemHolder.edtSellingPrice.setText(product.sellingPrice.toString())
        }

        itemHolder.tvPurchasePriceSummary.text = Constant.priceFormat.format(product.purchasePriceSummary)
        itemHolder.tvSellingPriceSummary.text = Constant.priceFormat.format(product.sellingPriceSummary)

        itemHolder.btnWeightOnStore.setOnClickListener { mainView.showSetWeightDialog(itemHolder.btnWeightOnStore, product, WeightEnum.WEIGHT_1) }
        itemHolder.btnWeightInFridge.setOnClickListener { mainView.showSetWeightDialog(itemHolder.btnWeightInFridge, product, WeightEnum.WEIGHT_2) }
        itemHolder.btnWeightInStorage.setOnClickListener { mainView.showSetWeightDialog(itemHolder.btnWeightInStorage, product, WeightEnum.WEIGHT_3) }
        itemHolder.btnWeight4.setOnClickListener { mainView.showSetWeightDialog(itemHolder.btnWeight4, product, WeightEnum.WEIGHT_4) }
        itemHolder.btnWeight5.setOnClickListener { mainView.showSetWeightDialog(itemHolder.btnWeight5, product, WeightEnum.WEIGHT_5) }
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder = MainHeaderViewHolder(view)

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val itemHolder = holder as MainHeaderViewHolder
        itemHolder.tvHeader.text = factory.name

        itemHolder.btnAddProduct.setOnClickListener {
            mainView.saveAll()
            mainView.addNewProduct(factory.id)
        }
        itemHolder.btnEditName.setOnClickListener {
            mainView.saveAll()
            mainView.showEditFactoryDialog(factory)
        }
        itemHolder.btnDelete.setOnClickListener {
            mainView.saveAll()
            mainView.deleteFactory(factory.id)
        }
    }

    override fun getFooterViewHolder(view: View): RecyclerView.ViewHolder = MainFooterViewHolder(view)

    override fun onBindFooterViewHolder(holder: RecyclerView.ViewHolder?) {
        val itemHolder = holder as MainFooterViewHolder
        footerHolder = itemHolder

        setFooterData()
        itemHolder.btnAddNew.setOnClickListener {
            mainView.saveAll()
            mainView.addNewProduct(factory.id)
        }
    }

    fun getViewData(): List<Product> {
        val products = mutableListOf<Product>()

        for (item in holders) {
            if (item.product != null) {
                try {
                    item.product!!.name = item.edtName.text.toString()
                    item.product!!.weightOnStore = prepareString(item.btnWeightOnStore.text.toString()).toFloatOrNull() ?: 0f
                    item.product!!.weightInFridge = prepareString(item.btnWeightInFridge.text.toString()).toFloatOrNull() ?: 0f
                    item.product!!.weightInStorage = prepareString(item.btnWeightInStorage.text.toString()).toFloatOrNull() ?: 0f
                    item.product!!.weight4 = prepareString(item.btnWeight4.text.toString()).toFloatOrNull() ?: 0f
                    item.product!!.weight5 = prepareString(item.btnWeight5.text.toString()).toFloatOrNull() ?: 0f
                    item.product!!.purchasePrice = prepareString(item.edtPurchasePrice.text.toString()).toFloatOrNull() ?: 0f
                    item.product!!.sellingPrice = prepareString(item.edtSellingPrice.text.toString()).toFloatOrNull() ?: 0f
                } catch (e: Exception) {
                    continue
                }
                products.add(item.product!!)
            }
        }


        return products
    }


    fun updateSummaryData() {
        for (item in holders) {
            if (item.product != null) {
                item.tvPurchasePriceSummary.text = Constant.priceFormat.format(item.product!!.purchasePriceSummary)
                item.tvSellingPriceSummary.text = Constant.priceFormat.format(item.product!!.sellingPriceSummary)
                setFooterData()
            }
        }
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

        footerHolder?.mainFooterPurchaseSummary?.text = Constant.priceFormat.format(purchaseSummary)
        footerHolder?.mainFooterSellingSummary?.text = Constant.priceFormat.format(sellingSummary)
    }

    private fun prepareString(string: String): String {
        return string
                .replace(',', '.')
                .replace(Constant.whiteSpaceRegex, "")
    }


}
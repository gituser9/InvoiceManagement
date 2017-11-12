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
import com.user.invoicemanagement.view.fragment.MainView
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
        var purchaseSummary = 0f
        var sellingSummary = 0f
        footerHolder = itemHolder

        list.forEach { product: Product ->
            purchaseSummary += product.purchasePriceSummary
            sellingSummary += product.sellingPriceSummary
        }

        itemHolder.mainFooterPurchaseSummary.text = purchaseSummary.toString()
        itemHolder.mainFooterSellingSummary.text = sellingSummary.toString()

        itemHolder.btnAddNew.setOnClickListener {
            mainView.saveAll()
            mainView.addNewProduct(factory.id)
        }
    }

    fun getViewData(): List<Product> {
        val products = mutableListOf<Product>()

        for (item in holders) {
            if (item.product != null) {
                val newProduct = Product()
                newProduct.id = item.product!!.id
                newProduct.factoryId = item.product!!.factoryId
                newProduct.name = item.edtName.text.toString()

                newProduct.weightOnStore = item.btnWeightOnStore.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
                newProduct.weightInFridge = item.btnWeightInFridge.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
                newProduct.weightInStorage = item.btnWeightInStorage.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
                newProduct.weight4 = item.btnWeight4.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
                newProduct.weight5 = item.btnWeight5.text.toString().replace(',', '.').toFloatOrNull() ?: 0f

                newProduct.purchasePrice = item.edtPurchasePrice.text.toString().toFloatOrNull() ?: 0f
                newProduct.sellingPrice = item.edtSellingPrice.text.toString().toFloatOrNull() ?: 0f

                products.add(newProduct)
            }
        }

        return products
    }


    fun updateSummaryData() {
//        updateProductView(itemHolder, product)
//        mainView.updateProduct(newProduct)
        for (item in holders) {
            if (item.product != null) {
                item.tvPurchasePriceSummary.text = Constant.baseFormat.format(item.purchasePriceSummary())
                item.tvSellingPriceSummary.text = Constant.baseFormat.format(item.sellingPriceSummary())
                setFooterData()
            }
        }

        /*itemHolder.tvPurchasePriceSummary.text = baseFormat.format(product.purchasePriceSummary)
        itemHolder.tvSellingPriceSummary.text = baseFormat.format(product.sellingPriceSummary)
        setFooterData()*/
    }

    private fun updateProductView(itemHolder: MainViewHolder, product: Product) {
        product.name = itemHolder.edtName.text.toString()

        product.weightOnStore = itemHolder.btnWeightOnStore.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        product.weightInFridge = itemHolder.btnWeightInFridge.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        product.weightInStorage = itemHolder.btnWeightInStorage.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        product.weight4 = itemHolder.btnWeight4.text.toString().replace(',', '.').toFloatOrNull() ?: 0f
        product.weight5 = itemHolder.btnWeight5.text.toString().replace(',', '.').toFloatOrNull() ?: 0f

        product.purchasePrice = itemHolder.edtPurchasePrice.text.toString().toFloatOrNull() ?: 0f
        product.sellingPrice = itemHolder.edtSellingPrice.text.toString().toFloatOrNull() ?: 0f
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


}
package com.user.invoicemanagement.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.data.WeightEnum
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.other.Constant
import com.user.invoicemanagement.view.adapter.holder.MainViewHolder
import com.user.invoicemanagement.view.fragment.MainTabFragment


class MainTabRecyclerViewAdapter(var view: MainTabFragment) : RecyclerView.Adapter<MainViewHolder>() {

    var items = mutableListOf<Product>()
    var holders = mutableListOf<MainViewHolder>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_main_item, parent, false)
        val holder = MainViewHolder(view)

        holders.add(holder)

        return holder
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val product = items[position]

        holder.product = product

        holder.edtName.setText(product.name)
        holder.btnWeightOnStore.text = product.weightOnStore.toString()
        holder.btnWeightInFridge.text = product.weightInFridge.toString()
        holder.btnWeightInStorage.text = product.weightInStorage.toString()
        holder.btnWeight4.text = product.weight4.toString()
        holder.btnWeight5.text = product.weight5.toString()

        holder.edtWeightOnStore.setText(product.weightOnStore.toString())
        holder.edtWeightInFridge.setText(product.weightInFridge.toString())
        holder.edtWeightInStorage.setText(product.weightInStorage.toString())
        holder.edtWeight4.setText(product.weight4.toString())
        holder.edtWeight5.setText(product.weight5.toString())

        if (product.purchasePrice != 0f) {
            holder.edtPurchasePrice.setText(product.purchasePrice.toString())
        }
        if (product.sellingPrice != 0f) {
            holder.edtSellingPrice.setText(product.sellingPrice.toString())
        }

        holder.tvPurchasePriceSummary.text = Constant.priceFormat.format(product.purchasePriceSummary)
        holder.tvSellingPriceSummary.text = Constant.priceFormat.format(product.sellingPriceSummary)

        holder.btnWeightOnStore.setOnClickListener { view.showSetWeightDialog(holder.btnWeightOnStore, product, WeightEnum.WEIGHT_1) }
        holder.btnWeightInFridge.setOnClickListener { view.showSetWeightDialog(holder.btnWeightInFridge, product, WeightEnum.WEIGHT_2) }
        holder.btnWeightInStorage.setOnClickListener { view.showSetWeightDialog(holder.btnWeightInStorage, product, WeightEnum.WEIGHT_3) }
        holder.btnWeight4.setOnClickListener { view.showSetWeightDialog(holder.btnWeight4, product, WeightEnum.WEIGHT_4) }
        holder.btnWeight5.setOnClickListener { view.showSetWeightDialog(holder.btnWeight5, product, WeightEnum.WEIGHT_5) }

        holder.setListener(View.OnLongClickListener {
            if (holder.product != null) {
                view.deleteProduct(holder.product!!, position)
            }
            true
        })
    }

    override fun getItemCount(): Int = items.size


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

                    products.add(item.product!!)
                } catch (e: Exception) {
                    continue
                }
            }
        }

        return products
    }

    fun updateSummaryData() {
        for (item in holders) {
            if (item.product != null) {
                item.tvPurchasePriceSummary.text = Constant.priceFormat.format(item.product!!.purchasePriceSummary)
                item.tvSellingPriceSummary.text = Constant.priceFormat.format(item.product!!.sellingPriceSummary)
            }
        }
    }

    // todo: to Common
    private fun prepareString(string: String): String {
        return string
                .replace(',', '.')
                .replace(Constant.whiteSpaceRegex, "")
    }
}

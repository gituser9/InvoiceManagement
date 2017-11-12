package com.user.invoicemanagement.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.user.invoicemanagement.model.dto.OldProduct
import com.user.invoicemanagement.model.dto.OldProductFactory
import com.user.invoicemanagement.other.Constant
import com.user.invoicemanagement.view.adapter.holder.ClosedInvoiceFooterViewHolder
import com.user.invoicemanagement.view.adapter.holder.ClosedInvoiceHeaderViewHolder
import com.user.invoicemanagement.view.adapter.holder.ClosedSectionItemViewHolder
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import java.text.NumberFormat
import java.util.*

class ClosedInvoiceSection(sectionParameters: SectionParameters, private val factory: OldProductFactory, private var list: List<OldProduct>) : StatelessSection(sectionParameters) {

    private val baseFormat = NumberFormat.getCurrencyInstance(Locale("ru"))


    override fun getContentItemsTotal(): Int = list.size

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder = ClosedSectionItemViewHolder(view)

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val itemHolder = holder as ClosedSectionItemViewHolder
        val product = list[position]

        itemHolder.edtName.text = product.name
        itemHolder.edtWeightOnStore.text = product.weightOnStore.toString()
        itemHolder.edtWeightInFridge.text = product.weightInFridge.toString()
        itemHolder.edtWeightInStorage.text = product.weightInStorage.toString()
        itemHolder.edtWeight4.text = product.weight4.toString()
        itemHolder.edtWeight5.text = product.weight5.toString()

        itemHolder.edtPurchasePrice.text = product.purchasePrice.toString()
        itemHolder.edtSellingPrice.text = product.sellingPrice.toString()

        itemHolder.tvPurchasePriceSummary.text = Constant.priceFormat.format(product.purchasePriceSummary)
        itemHolder.tvSellingPriceSummary.text = Constant.priceFormat.format(product.sellingPriceSummary)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder = ClosedInvoiceHeaderViewHolder(view)

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val itemHolder = holder as ClosedInvoiceHeaderViewHolder
        itemHolder.tvHeader.text = factory.name
    }

    override fun getFooterViewHolder(view: View): RecyclerView.ViewHolder = ClosedInvoiceFooterViewHolder(view)

    override fun onBindFooterViewHolder(holder: RecyclerView.ViewHolder?) {
        val itemHolder = holder as ClosedInvoiceFooterViewHolder
        var purchaseSummary = 0f
        var sellingSummary = 0f

        list.forEach { product: OldProduct ->
            purchaseSummary += product.purchasePriceSummary
            sellingSummary += product.sellingPriceSummary
        }

        itemHolder.mainFooterPurchaseSummary.text = purchaseSummary.toString()
        itemHolder.mainFooterSellingSummary.text = sellingSummary.toString()
    }
}
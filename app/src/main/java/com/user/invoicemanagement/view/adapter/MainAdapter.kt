package com.user.invoicemanagement.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
/*
import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter
import com.afollestad.sectionedrecyclerview.SectionedViewHolder
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.dto.ProductFactory
import kotlinx.android.synthetic.main.fragment_main_item.view.*
import kotlinx.android.synthetic.main.fragment_main_header.view.*


class MainAdapter : SectionedRecyclerViewAdapter<MainAdapter.ViewHolder>() {

    var list: List<ProductFactory> = emptyList()      // TODO: replace by view model


    override fun getItemCount(section: Int): Int {
        return list[section].products.size
    }

//    override fun getSectionCount(): Int = list.size
    override fun getSectionCount(): Int {
        return list.sumBy { it.products.size }
    }

    override fun onBindViewHolder(holder: ViewHolder, section: Int, relativePosition: Int, absolutePosition: Int) {
        if (list[section].products.isEmpty()) {
            return
        }

        val product = list[section].products[relativePosition]
        holder.edtName?.setText(product.name!!)
        holder.edtWeightOnStore?.setText(product.weightOnStore.toString())
        holder.edtWeightInFridge?.setText(product.weightInFridge.toString())
        holder.edtWeightInStorage?.setText(product.weightInStorage.toString())
        holder.edtPurchasePrice?.setText(product.purchasePrice.toString())
        holder.edtSellingPrice?.setText(product.sellingPrice.toString())
    }

    override fun onBindFooterViewHolder(holder: ViewHolder, section: Int) {

    }

    override fun onBindHeaderViewHolder(holder: ViewHolder, section: Int, expanded: Boolean) {
        holder.tvHeader?.text = list[section].name
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutId = when (viewType) {
            VIEW_TYPE_HEADER -> R.layout.fragment_main_header
            VIEW_TYPE_ITEM -> R.layout.fragment_main_item
            else -> R.layout.fragment_main_footer
        }
        val view = LayoutInflater.from(parent?.context).inflate(layoutId, parent, false)

        return ViewHolder(view, this)
    }


    // View Holder
    class ViewHolder(itemView: View, private val adapter: MainAdapter) : SectionedViewHolder(itemView), View.OnClickListener {

        val tvHeader: TextView? = itemView.tvHeader

        val edtName: EditText? = itemView.edtName

        val edtWeightOnStore: EditText? = itemView.edtWeightOnStore
        val edtWeightInFridge: EditText? = itemView.edtWeightOnFridge
        val edtWeightInStorage: EditText? = itemView.edtWeightOnStorage

        val edtPurchasePrice: EditText? = itemView.edtPurchasePrice
        val edtSellingPrice: EditText? = itemView.edtSellingPrice


        override fun onClick(v: View?) {
            if (isHeader) {
                adapter.toggleSectionExpanded(relativePosition.section())
            }
        }
    }
}*/
